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

import java.util.Date;

public class ImageDetails
{
  private String id;
  private String name;
  private Date lastUpdateDateTime;
  private String serverId;

  public ImageDetails(String id, String name, Date lastUpdateDateTime, String serverId)
  {
    this.id = id;
    this.name = name;
    this.lastUpdateDateTime = lastUpdateDateTime;
    this.serverId = serverId;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Date getLastUpdateDateTime()
  {
    return lastUpdateDateTime;
  }

  public void setLastUpdateDateTime(Date lastUpdateDateTime)
  {
    this.lastUpdateDateTime = lastUpdateDateTime;
  }

  public String getServerId()
  {
    return serverId;
  }

  public void setServerId(String serverId)
  {
    this.serverId = serverId;
  }

  @Override
  public String toString()
  {
    return "ImageDetails [id=" + id + ", name=" + name + ", lastUpdateDateTime=" + lastUpdateDateTime + ", serverId="
        + serverId + "]";
  }

  public long getAgeInDays()
  {
    return Math.round(Math.floor((System.currentTimeMillis() - lastUpdateDateTime.getTime()) / (24 * 60 * 60 * 1000)));
  }

}
