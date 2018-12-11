# seetest.pwa.demo - Example for loading Progressive Web App (PWA) across various browsers in seetest cloud.

This project demonstrates loading of Progressive Web across various browser's in Device (Android and IOS) and Desktop.

This example will cover:

1. Installs PWA ( Only in  Android Device and Chrome Browser)
2. Performs a simple test with a configured URL which supports PWA.


### Steps to run demo test

1. Clone this git repository

	```
	git clone
	```

2. Obtain access key from seetest.io cloud

    https://docs.seetest.io/display/SEET/Execute+Tests+on+SeeTest+-+Obtaining+Access+Key

    note :  you need to have a valid subscription or a trial to run this test (Trial \ paid)

3. To run the tests,

    Please ensure that following environment variables are set.

    1. JAVA_HOME to JDK/JRE HOME and update it in the PATH variable.
    2. SEETEST_IO_ACCESS_KEY to valid access key obtained before in Step 2.

        In Windows:

        ```
    	set SEETEST_IO_ACCESS_KEY=<your access key>
    	```

    	In Unix:

    	```
        export SEETEST_IO_ACCESS_KEY=<your access key>
        ```

    Now run the tests using following command in command line.

    ```
    gradlew runTests
    ```

    ### Note :
    If you are using IDE like IntelliJ, make sure you create a Run configuration to use src/main/java/testng.xml.
    Set the Environment variable and then proceed to execute the test.

    ![Scheme](images/IntelliJ_Run_Conf.gif)

5. To Run tests in parallel, use src/main/java/testng-parallel-class.xml file

	Right click on the file and run from IntelliJ \ Eclipse
	Or use the command line :

	```
	gradlew runTestsParallel
	```
s