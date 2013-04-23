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
package com.rabidgremlin.serverimager.opts;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class MainOptions
{
  @Parameter(names = "-user", description = "Rackspace API User name.", required = true)
  public String username;

  @Parameter(names = "-key", description = "Rackspace API Key.", required = true)
  public String apiKey;

  @Parameter(names = "-prefix", description = "Image name prefix. Only images starting with this text are touched by this tool.")
  public String imageNamePrefix = "si";

  @Parameter(names = "-nuke", description = "If set then old images are deleted. Use -maxage to specify what is an 'old' image.")
  public boolean nukeOldImages = false;

  @Parameter(names = "-maxage", description = "The max age of images in days. Images older then this are deleted if the -nuke option is specified.")
  public int maxImageAgeInDays = 7;

  @Parameter(description = "servers", required = true)
  public List<String> servers = new ArrayList<String>();

  @Parameter(names = "-zone", description = "The Rackspace zone.")
  public String rackspaceZone = "DFW";

  @Parameter(names = "-debug", description = "Turn on debug logging")
  public boolean debug = false;

  @Parameter(names = "-nocreate", description = "Don't create any images")
  public boolean noImageCreation = false;
}
