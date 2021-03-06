# UTCIS
<h3>Created by The University of Texas, College of Liberal Arts Informational Technology Services</h3>
<h3>This tool is used to collect course information from the UT Course Instructor Survey website. (https://utdirect.utexas.edu/ctl/ecis/results/search.WBX)</h3> 
<h3>The tool is able to search for classes by professor or by the course name. The output will be an excel sheet with all the data collected.</h3>

Installation: (Java 8 Runtime Environment or greater required from:https://java.com/en/download/)
(If you do not have the .jar file yet, make sure to compile the java packages in the src folder into a jar executable. Also make sure to have the Java 8 SDK installed on your device from: https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
1. To install UTCIS, simply drag and drop the UTCIS.jar file to the desired run location. 
2. Then, download chromedriver.exe from the following locations (stable release specific to your operating system):
	http://chromedriver.chromium.org/

3. Unzip the file if need be and place the driver in a known place on your computer. The current chromedriver from this github repo is for linux operating systems. Be sure to replace it if you are on a different operating system.
4. Make sure your current version of Google Chrome is updated to the latest version.

Setup:
1. Run the UTCIS.jar file. ($java -jar UTCIS.jar)
2. Upon initial setup, the screen shood ask you to set a preference for which driver to use and it's location.
3. Select the driver you wish to use and then browse to the location of said driver.
4. Hit select.

Usage:
1. To use, please format a text file as such:<br /><br />
	[courses] or [professor] (depending on which feild you want to use)
	
	[dept]-[class num]<br />
	
	ex:<br />
	courses<br />
	C S-331<br />
	C S-429<br />
	
	ex:<br />
	professor<br />
	Gilbert, John E<br />
	Patt, Yale N<br />

	Please make sure to fill the department or professor out exactly as it appears in the drop down 	menu of the site, and to include an enter(new line) after each class/professor <b>(except the last entry)</b>.
	
	Ex:<br />
	A B-123<br />
	AB -123 <br />
	(notice the inclusion of the spaces)<br />
	
	<br />***Please Note: The current version of UTCIS has a specific bug where looking for classes like C S-331, will result in getting classes C S-331K as well. This should be worked out in later editions, but is how it is currently.***

2. After filling out said text file, save it and run the UTCIS.jar
3. Hit "Browse" and browse to your text file location to load the classes.
4. After selecting your file, please title your file. As a default, it should save within your current working directory, but if you wish to have it stored at a different location, hit "Save As" and browse to said location.

	***Please Note: Please title the file "<name>.xlsx" . Current versions of the UTCIS software do not append the file type at the end of the name and thus will save it as a "file" instead of an Excel document if this ending is not added.***

	***If you forget to do this, you can simply rename the file "<name>.xlsx" after it is exported. You do NOT have to run the program over the same file again.***
5. Enter your login information (as a note, this is never saved and is wiped as soon as submit is hit making sure it is never logged and is secure).
6. Once you are ready, hit submit. If no errors pop up, it should be running. Once the "Start" button pops back up, your file should have been exported to the selected location and be good to go.
	
	***Please note: The program may appear to be frozen after clicking start when using chromedriver. It is not. It is set to run in "headless" mode which hides the browser from sight.***

For any questions and concerns, please contact: roger.terrazas@utexas.edu
