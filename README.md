# Growler label printing and management

Single fat-jar Spring boot project for managing printing of growler stickers for growler sales.

## Getting Started

This includes a web interface for printing and managing brews.
Printed on a Dymo 450 printer with 1" labels.

### Prerequisites

Raspberry PI (I used a pi zero W):
Java 8
WiringPi

### Installing

####PI Install:
 - Install Raspbian Image
 - Install Java 8: sudo apt-get install openjdk-8-jdk
 - Install WiringPI: sudo apt-get install wiringpi
 - Install CUPS: sudo apt-get install libcups2-dev libcupsimage2-dev g++ cups cups-client links2

####Dymo Drivers (quite problematic, but works with these steps)
 - Get drivers for Dymo: https://www.dymo.com/en-US/dymo-label-sdk-cups-linux-p?storeId=20051&catalogId=10551#

 - tar xvf dymo-cups-drivers-1.4.0.5.tar.gz
 - git clone https://aur.archlinux.org/dymo-cups-drivers.git
 - cp -a dymo-cups-drivers/. dymo-cups-drivers-1.4.0.5/
 - cd dymo-cups-drivers-1.4.0.5
 - patch -Np1 -i cups-ppd-header.patch
 - sudo ./configure
 - sudo make
 - sudo make install
 - sudo usermod -a -G lpadmin pi

####Configure CUPS for remote access
 - Open the file /etc/cups/cupsd.conf (ex. sudo nano /etc/cups/cupsd.conf - note: use crtl+x then "y" if using nano to save)
 - In the blocks <Location /> , <Location /admin> and <Location /admin/conf>, add Allow All (or secure it better if you need to)
 - In the "Listen" section, replace the loopback localhost Listen with: Port 631 (no Listen, just Port 631). Or secure it better if you need to.
 -- https://help.ubuntu.com/lts/serverguide/cups.html
 - restart CUPS (sudo systemctl restart cups.service)

####Add Printer
 - Navigate to http://[ip]:631
 - Add printer (make sure it is hooked up and on)
 - Add the Dymo
 -- Probably don't need to share it.

####Run Application
 - Build source with Maven
 - Copy target to PI
 - Start far jar with: sudo java -Dspring.profiles.active=pi -jar growler-brewprinter-0.0.1-SNAPSHOT.jar
 
Add to startup once happy with it:
 - sudo nano /etc/rc.local
 - sudo java -Dspring.profiles.active=pi -jar /home/pi/growler-brewprinter-0.0.1-SNAPSHOT.jar & > /home/pi/brewlog.txt 2>&1

### Wiring
Code uses Pi4J. It has it's own wiring diagram (https://pi4j.com/1.2/pins/model-zerow-rev1.html)
Code sets up listeners for buttons in the ControllerConfig component. Modify to your needs.


## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Troubleshooting
If you get a "file not found" error when printing, you may need to install cups-bsd (did with a PI Zero W)