# Fortify Web App Demo

This is an example project for the demonstration of Fortify SAST and DAST vulnerability scanning 

To use this demo in full you will need the following software installed:

* [Fortify SCA and Tools](https://www.microfocus.com/en-us/cyberres/application-security/static-code-analyzer)
* [Fortify WebInspect](https://www.microfocus.com/en-us/cyberres/application-security/webinspect)  

Setup
-----

First create a file called `.env` in the project root directory with content similar to the following:

```
# The URL of Software Security Center
SSC_URL=http://ftfydemo:8080/ssc
SSC_USERNAME=admin
SSC_PASSWORD=admin
# SSC Authentication Token (recommended to use CIToken)
SSC_AUTH_TOKEN=XXXXX
# Name of the application in SSC
SSC_APP_NAME=FortifyWebAppDemo
# Name of the application version in SSC
SSC_APP_VER_NAME=main
```

If you want to upload the results to Fortify Software Security Center, make sure you set
appropriate values for `SSC_URL` and `SSC_AUTH_TOKEN`.

Security Scan
-------------

To run a Fortify Static Code Analyzer scan you can use the included script `fortify-sca.ps1` as follows:

```
powershell .\bin\fortify-sca.ps1
```

This will scan the applications source code.

An example results file (in PDF) is available [here](samples/FortifyWebAppDemo.pdf).

To view the full results yourself you can use:

```
auditworkbench .\FortifyWebAppDemo.fpr
```

There should be a number of issues including (but not limited to):

- TBD

Run Application (locally)
-------------------------

If you want to run the application locally you will need to have [MySQL](https://www.mysql.com/) installed and set a system
environment called `MYSQLCONNSTR_defaultConnection` with a value similar to the following:

```
Database=products;Data Source=localhost;User Id=mysql;Password=mysql
```

Make sure the userid/password is valid for your local MySQL instance and that the database referred to is already created.

You can the run the application using the following:

```
.\gradlew.bat bootRun
```

The application should then be available at the URL: `http://localhost:8080`. If it fails to start make sure you have
no other applications running on port 8080.


Deploy Application
------------------

TBD

---

Kevin A. Lee (kadraman) - kevin.lee@microfocus.com
