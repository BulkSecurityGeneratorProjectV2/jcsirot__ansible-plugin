/*
 *     Copyright 2015 Jean-Christophe Sirot <sirot@chelonix.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jenkinsci.plugins.ansible;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.BuildListener;
import hudson.util.ArgumentListBuilder;
import org.kohsuke.stapler.DataBoundConstructor;

public class InventoryPath extends Inventory
{
    public final String path;

    @DataBoundConstructor
    public InventoryPath(String path) {
        this.path = path;
    }

    @Override
    public InventoryHandler getHandler()
    {
        return new InventoryHandler()
        {
            public void addArgument(ArgumentListBuilder args, EnvVars envVars, BuildListener listener)
            {
                String expandedPath = envVars.expand(InventoryPath.this.path);
                args.add("-i").add(expandedPath);
            }

            public void tearDown(BuildListener listener)
            {
            }
        };
    }

    @Extension
    public static class DescriptorImpl extends InventoryDescriptor {

        @Override
        public String getDisplayName() {
            return "File";
        }
    }

}