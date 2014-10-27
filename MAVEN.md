## To use JLibIBuddy in your project
 ##


## Libusb-win32 installation ##

First of all you need to install libusb-win32. Follow the instructions there: [http://libusb-win32.sourceforge.net/#installation](http://libusb-win32.sourceforge.net/#installation).

### If you don't use maven 2 ###

- download and and extract jlibibuddy-1.1-dist.zip
- add jlibibuddy-1.1.jar and lib/*.jar to your project classpath
- copy lib/libusbjava-native-win32-0.2.3.0.dll to C:\Windows\System32 and rename it to LibusbJava.dll (it must have this name otherwise it won't work).

##If you use maven 2


- add the repository (where to find JLibIBuddy) to your pom:

    	<repository>
	    	<id>JRAF.org</id>
	    	<name>JRAF.org Maven Repository</name>
	    	<url>http://www.JRAF.org/static/maven/2</url>
	    	<layout>default</layout>
    	</repository>



- add the dependency to JLibIBuddy to your pom:

        <dependency>
            <groupId>org.jraf</groupId>
            <artifactId>jlibibuddy</artifactId>
            <version>1.1</version>
            <scope>compile</scope>
        </dependency>
- add this plugin to your pom:

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <executions>
                <execution>
                    <id>install-libusbjava-dll</id>
                    <phase>install</phase>
                    <goals>
                        <goal>copy</goal>
                    </goals>
                    <configuration>
                        <artifactItems>
                            <artifactItem>
                                <groupId>libusbjava</groupId>
                                <artifactId>libusbjava-native-win32</artifactId>
                                <version>0.2.3.0</version>
                                <type>dll</type>
                            </artifactItem>
                        </artifactItems>
                        <outputDirectory>c:\windows\system32\</outputDirectory>
                        <destFileName>LibusbJava2.dll</destFileName>
                    </configuration>
                </execution>
            </executions>
        </plugin>

This will copy the dll to the proper place when you invoke the install goal. Sorry I don't know if there is a way to do that in JLibIBuddy's pom directly so for now you have to do it in your own pom.