# Server Imager
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
		   
## Building

1. check out source
2. ```cd build```
3. ```ant release```

*serverimager.jar* will be created in ```..\release``` folder		   
		   
## Thanks
This tool was created with the support of [ClearPoint New Zealand](http://www.clearpoint.co.nz). Thanks !		   