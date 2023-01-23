# Fortify Demo App

This is a sample Java/Spring web application that can be used for the demonstration of [Fortify on Demand](https://www.microfocus.com/en-us/cyberres/application-security/fortify-on-demand)
and [Debricked](https://debricked.com/). It is a cut down "search" results/details page from a larger sample application 
[IWAPharmacyDirect](https://github.com/fortify-presales/IWAPharmacyDirect) and is kept deliberately small for demos.

To use this demo in full you will need:

* a [Fortify on Demand](https://www.microfocus.com/en-us/cyberres/application-security/fortify-on-demand) tenant, account and application subscription
* a [Debricked](https://debricked.com/) account  
* a [Fortify CLI installation](https://fortify-ps.github.io/fcli/)
* *optional* A Microsoft Azure Subscription and [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli) for deploying application to Azure

Setup
-----

First create a file called `.env` in the project root directory with content similar to the following:

```
# Fortify on Demand
FOD_API_URL=https://api.xxx.fortify.com
FOD_API_KEY=XXX
FOD_API_SECRET=XXX
# Azure (Resource Manager)
AZURE_SUBSCRIPTION_ID=XXX
AZURE_RESOURCE_GROUP=fortify-demo-rg
# change the below to a unique name, e.g. "YOUR_INITIALS-fortify-demo-app"
AZURE_APP_NAME=KAL-fortify-demo-app
AZURE_REGION=eastus
```

Replace all the `XXX` and `YYY` values with values appropriate to your environment.

Please do NOT add this file to source control.

Run Application (locally)
-------------------------

You can the run the application locally using the following:

```
gradlew bootRun
```

The application should then be available at the URL `http://localhost:8088`. If it fails to start,
make sure you have no other applications running on port 8088. There are only a few features that are
functional in this version of the app:

 - you can type in some keywords in the search box, e.g. "alphadex" to filter results
 - you can click on any search result to navigate to a details page
 - you can download a datasheet PDF from a details page
 - you can subscribe to the newsletter by entering an email address in the input field of the footer
 - you can login/logout

These have been "enabled" because they all have potential security issues that can be found by Fortify.

Deploy Application (Azure)
--------------------------

If you want to run the application in the cloud (to run a Fortify on Demand DAST scan) you can deploy the application 
to Microsoft Azure along with its required infrastructure by using the following (from a Windows command prompt):

```
az login [--tenant XXXX]
az group create --name [YOUR_INITIALS]-fortify-demo-rg --location eastus
gradlew azureWebAppDeploy
```

Replace `eastus` with your own desired region and make sure in the `.env` file you have
set `AZURE_APP_NAME` to a unique value.

You can navigate to your [Azure portal](https://portal.azure.com/#home) to see the built infrastructure and to
the deployed web application using the URL output shown from the `azureWebAppDeploy task`.

Remove Application and Infrastructure
-------------------------------------

To clean up all the resources you can execute the following (from a Windows command prompt):

```
az group delete --name [YOUR_INITIALS]-fortify-demo-rg

```

Application Security Testing
----------------------------

***Fortify on Demand***

To package the application's source code and start a Fortify on Demand SAST scan carry out the following:

```
scancentral package -bt gradle -o FoDUpload.zip
fcli fod session login --url https://api.ams.fortify.com --tenant [YOUR-TENANT] --user [YOUR-USER-LOGIN] --password [YOUR-PASSWORD]
...
fcli fod session logout
```

This will scan the applications source code.

An example results file (in PDF) is available [here](samples/fod-sast.pdf).

There should be a number of issues including (but not limited to):

- SQL Injection
- JSON Injection
- Path Manipulation  
- Insecure Randomness
- Empty Password in Configuration File


---

Kevin A. Lee (kadraman) - kevin.lee@microfocus.com
