# Rackspace Cloud Server Imager
This tool creates rolling server images for Rackspace Cloud servers.

## Usage

	Usage: java -jar serverimager.jar [options] servers
	  Options:
		-debug
		   Turn on debug logging
		   Default: false
	  * -key
		   Rackspace API Key.
		-maxage
		   The max age of images in days. Images older then this are deleted if the
		   -nuke option is specified.
		   Default: 7
		-nocreate
		   Don't create any images
		   Default: false
		-nuke
		   If set then old images are deleted. Use -maxage to specify what is an
		   'old' image.
		   Default: false
		-prefix
		   Image name prefix. Only images starting with this text are touched by
		   this tool.
		   Default: si
	  * -user
		   Rackspace API User name.
		-zone
		   The Rackspace zone.
		   Default: DFW

## Examples
If you have servers *zeus*, *apollo* and *mars* the following command run every 24hrs (via cron for instance) will create rolling daily images:

	java -jar serverimager.jar -user myuserid -key xxxxxxxyyyyyyyzzzzz -maxage 1 -nuke -prefix daily zeus apollo mars
	
If you run the the following command every 7 days, you will create a rolling weekly image:

	java -jar serverimager.jar -user myuserid -key xxxxxxxyyyyyyyzzzzz -maxage 1 -nuke -prefix weekly zeus apollo mars
		   
**Note** You can only create one image per server at a time so make sure to schedule the commands above at different times. Also the above commands will land up nuking the current image before the new one is completed created. To avoid this you can:

* Run the command with a smaller window (eg every 23hrs instead of 24 hours for the daily example)
* Add one to the ```-maxage``` parameter 
* Run the command create and nuke commands at seperate times e.g. run the tool every 24 hours (without the ```-nuke``` parameter) and then run it again with the ```-nuke``` and ```-nocreate``` params every 25 hours

### @file usage
You can also supply the options for the tool via a file. For instance

	java -jar serverimager.jar @dailyimages
	
where *dailyimages* is file containing:

	-user 
	myuserid 
	-key 
	xxxxxxxyyyyyyyzzzzz 
	-maxage 
	1 
	-nuke 
	-prefix 
	daily 
	zeus 
	apollo 
	mars	
		   
## Building

1. check out source
2. ```cd build```
3. ```ant release```

*serverimager.jar* will be created in ```..\release``` folder		   
		   
## Thanks
This tool was created with the support of [ClearPoint New Zealand](http://www.clearpoint.co.nz). Thanks !		   