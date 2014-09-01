/**
 *  Copyright 2010 SingleMind Consulting, Inc. (http://singlemindconsulting.com)
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 */
package org.ideaproject.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.annotations.security.permission.PermissionAction;
import org.jboss.seam.annotations.security.permission.PermissionDiscriminator;
import org.jboss.seam.annotations.security.permission.PermissionRole;
import org.jboss.seam.annotations.security.permission.PermissionTarget;
import org.jboss.seam.annotations.security.permission.PermissionUser;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "user_permissions")
public class UserPermissions implements Serializable
{
   private static final long serialVersionUID = -5628863031792429938L;
   
   @Id @GeneratedValue
   private Integer id;

   @PermissionUser 
   @PermissionRole
   private String recipient;

   @PermissionTarget
   private String target;

   @PermissionAction
   private String action;

   @PermissionDiscriminator
   private String discriminator;
   
   public Integer getId()
   {
      return id;
   }
   
   public void setId(Integer id)
   {
      this.id = id;
   }
   
   public String getRecipient()
   {
      return recipient;
   }
   
   public void setRecipient(String recipient)
   {
      this.recipient = recipient;
   }

   public String getTarget()
   {
      return target;
   }
   
   public void setTarget(String target)
   {
      this.target = target;
   }

   public String getAction()
   {
      return action;
   }
   
   public void setAction(String action)
   {
      this.action = action;
   }
   
   public String getDiscriminator()
   {
      return discriminator;
   }
   
   public void setDiscriminator(String discriminator)
   {
      this.discriminator = discriminator;
   }

}
