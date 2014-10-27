
Fork of [http://www.jraf.org/static/maven/sites/jlibibuddy/](http://www.jraf.org/static/maven/sites/jlibibuddy/).

----------

# JLibIBuddy #

A Java library to control an i-Buddy (a 3-inch tall blinking and moving USB figureine, see http://www.i-buddy.com/ ). Currently it is only supported / tested under Windows.

[I-BuddyShop](http://www.i-buddyshop.com/)

In [.NET](http://ibuddylib.sourceforge.net/) or [python](https://code.google.com/p/pybuddy/)

[JLibIBuddy](http://www.jraf.org/static/maven/sites/jlibibuddy/)

Needs [Java libusb / libusb-win32](http://libusbjava.sourceforge.net/wp/?page_id=9)

[Benoit Lubek](https://github.com/BoD "BoD")


Original maven repo http://www.jraf.org/static/maven/2/org/jraf/jlibibuddy/

We need to create backup archives for artifacts **org.jraf:jlibibuddy:1.1** ([see](http://libusbjava.sourceforge.net/wp/) and the [sourceforge](http://sourceforge.net/projects/libusbjava/)) and **libusbjava:libusbjava-native-win32:0.2.3.0** which are hosted on a [private maven repository](http://www.JRAF.org/static/maven/2). The Libusbjava code have been forked:

- [https://github.com/Boukefalos/libusbjava.cpp](https://github.com/Boukefalos/libusbjava.cpp)
- [https://github.com/Boukefalos/libusbjava.java](https://github.com/Boukefalos/libusbjava.java)


tedious to get libusb working while porting to linux, use other project instead:

- [https://github.com/tietomaakari/ibuddy-lkm](https://github.com/tietomaakari/ibuddy-lkm)

still this git fork should once be done

and we have te maven artifact in gradle


## Installation ##

For linux, use [libusb](http://sourceforge.net/projects/libusb/)?



## Usage ##

Using the library is very simple as shown by this example:

	import org.jraf.jlibibuddy.IBuddy;
	import org.jraf.jlibibuddy.IBuddyException;
	import org.jraf.jlibibuddy.IBuddyUtils;
	
	
	(...)
	
	
	// get the i-Buddy
	IBuddy iBuddy = IBuddy.getIBuddy();
	
	// send some low level commands directly
	iBuddy.sendHeadColor(IBuddy.Color.RED);
	iBuddy.sendHeart(true);
	
	// use IBuddyUtils to send higher levels commands
	IBuddyUtils.flap(iBuddy, 400, 7);
	
	
	(...)

## Credits ##

Most of the USB code was done following the code posted by "Tom" at [http://cuntography.com/blog/?p=17](http://cuntography.com/blog/?p=17). (dead)

## Contact ##
Contact the author: [BoD@JRAF.org](mailto:BoD@JRAF.org) .

