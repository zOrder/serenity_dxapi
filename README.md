# Serenity-BDD-with-Cucumber-and-Rest-Assured


## to run the tests on your machine
- follow instructions from https://academy.pega.com/challenge/exploring-dx-api-postman/v2/in/81486?
- create OAuth 2.0 Client Registration
- copy properties from downloaded .txt to serenity.properties
- run mvn clean verify on terminal

## workflow during development and learnings
- make sure to store the etag from each API response
- you will need to provide the etag in each following step otherwise you will receive a 409 "errorClassification": "Resource is stale"
---
- for each step you add go to app studio and open you browser developer tools
- while you process in app studio through your case you see the corresponding API requests
- you can copy the request as cUrl and use it as a blueprint for you next @When function
- have a closer look at your response data to write @Then assertions
- - e.g. to observe a status change you check the body of your response => response.then().body("data.caseInfo.status", equalTo("Pending-Dispatch"));
- if properly prompted a LLM can provide good results based on the cURL

--- 

## it's still a prototype so what's missing
- don't use serenity.properties to store credentials, you can provide them via ENV variables on the CI
- payloads should all be extracted to resource files, i have only done it for the customer payload
- make use of test setup and teardown functionality
- i did not spend much time on the pom.xml - it works but it's not great
- we might make use of libs like java-faker to crate more random user data