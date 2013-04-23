/* Copyright 2013 Rabidgremlin Limited

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.rabidgremlin.serverimager.api;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.config.ComputeServiceProperties;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.NovaAsyncApi;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.features.ImageApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.rest.RestContext;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

public class ApiHelper
{
  private static Logger log = LoggerFactory.getLogger(ApiHelper.class);

  private static final String POLL_PERIOD_TWENTY_SECONDS = String.valueOf(SECONDS.toMillis(20));
  private ComputeService compute;
  private RestContext<NovaApi, NovaAsyncApi> nova;
  private ServerApi serverApi;
  private ImageApi imageApi;

  public ApiHelper(String username, String apiKey, String zone)
  {
    String provider = "rackspace-cloudservers-us";

    Iterable<Module> modules = ImmutableSet.<Module> of(new SshjSshClientModule(), new SLF4JLoggingModule());

    // These properties control how often jclouds polls for a status udpate
    Properties overrides = new Properties();
    overrides.setProperty(ComputeServiceProperties.POLL_INITIAL_PERIOD, POLL_PERIOD_TWENTY_SECONDS);
    overrides.setProperty(ComputeServiceProperties.POLL_MAX_PERIOD, POLL_PERIOD_TWENTY_SECONDS);

    ComputeServiceContext context = ContextBuilder.newBuilder(provider).credentials(username, apiKey).modules(modules)
        .buildView(ComputeServiceContext.class);

    compute = context.getComputeService();
    nova = context.unwrap();
    serverApi = nova.getApi().getServerApiForZone(zone);
    imageApi = nova.getApi().getImageApiForZone(zone);
  }

  public String createImage(String imageName, String serverId)
  {
    log.debug("Requesting creation of image {} for server {}", imageName, serverId);
    return serverApi.createImageFromServer(imageName, serverId);
  }

  public void deleteImage(String imageId)
  {
    log.debug("Requesting deletaion of image {}", imageId);
    imageApi.delete(imageId);
  }

  public List<ImageDetails> getImages(String prefix, Set<String> serverIds)
  {
    log.info("Fetching list of images...");
    FluentIterable<? extends Image> images = imageApi.listInDetail().concat();

    List<ImageDetails> imageDetailsList = new ArrayList<ImageDetails>();

    for (Image image : images)
    {
      if (image.getName().startsWith(prefix) && image.getServer() != null && serverIds.contains(image.getServer().getId()))
      {
        log.debug("Adding image {} for server {} to the list", image.getName(), image.getServer().getId());
        imageDetailsList.add(new ImageDetails(image.getId(), image.getName(), image.getUpdated(), image.getServer().getId()));
      }
      else
      {
        log.debug("Skipping image {}. Does not match prefix or server isn't in list", image.getName());
      }
    }

    log.info("Found {} images with a prefix {} and a server id in list {}", new Object[]{ imageDetailsList.size(), prefix,
        Joiner.on(", ").join(serverIds) });
    return imageDetailsList;
  }

  public List<ServerDetails> getServers()
  {
    log.info("Fetching list of servers...");
    FluentIterable<? extends Server> servers = serverApi.listInDetail().concat();

    List<ServerDetails> serverDetailsList = new ArrayList<ServerDetails>();

    for (Server server : servers)
    {
      serverDetailsList.add(new ServerDetails(server.getId(), server.getName()));
    }

    log.info("Found {} servers", serverDetailsList.size());
    return serverDetailsList;
  }

  public void close()
  {
    if (compute != null)
    {
      compute.getContext().close();
    }
  }

}
