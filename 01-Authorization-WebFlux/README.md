# Authorization with Spring WebFlux

This sample demonstrates:

- Configuring a Spring Boot WebFlux application as a Resource Server
- Using and extending Spring Security to validate JWTs
- Protecting APIs to only allow authorized access

## Prerequisites

- Java 8 or greater
- An Auth0 account

## Setup

> For complete instructions and additional information, please refer to the [Spring 5 API Security Quickstart](https://auth0.com/docs/quickstart/backend/java-spring-security5) that this sample accompanies.

### Create an Auth0 API

In the [APIs](https://manage.auth0.com/dashboard/#/apis) section of the Auth0 dashboard, click Create API. Provide a name and an identifier for your API, for example `https://quickstarts/api`. Leave the Signing Algorithm as RS256.

### Configure the project

The project needs to be configured with your Auth0 domain and API Identifier.

To do this, first copy `src/main/resources/application.yml.example` into a new file in the same folder called `src/main/resources/application.yml`, and replace the values with your own Auth0 domain and API Identifier:

```yaml
auth0:
  audience: {API_IDENTIFIER}

spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          # Note the trailing slash is important!
          issuer-uri: https://{DOMAIN}/
```

## Running

You can run the application using Gradle or Docker.

### Gradle

Linux / MacOS:
```bash
./gradlew clean bootRun
```

Windows:
```bash
gradle clean bootRun
```

### Docker

Linux / MacOS:
```bash
sh exec.sh
```

Windows:
```
./exec.ps1
```

## Running unit tests

Linux / macOS:

```bash
./gradlew clean test
```

Windows:

```bash
gradlew.cmd clean test
```

## Testing the secured APIs

Using a REST client such as Postman or cURL, issue a `GET` request to `http://localhost:3010/api/public`. You should receive the response:

```json
{"message":"All good. You DO NOT need to be authenticated to call /api/public."}
```

Next, issue a `GET` request to `http://localhost:3010/api/private`. You should receive a `401 Unauthorized` response.

To test that your API is properly secured, you can obtain a test token in the Auth0 Dashboard:

1. Go to the **Machine to Machine Applications** tab for the API you created above.
2. Ensure that your API test application is marked as authorized.
3. Click the **Test** tab, then **COPY TOKEN**.

Issue a `GET` request to the `/api/private` endpoint, this time passing the token you obtained above as an `Authorization` header set to `Bearer YOUR-API-TOKEN-HERE`. You should then see the response:

```json
{"message":"All good. You can see this because you are Authenticated."}
```

Finally, to test that the `/api/private-scoped` is properly protected by the `read:messages` scope, make a `GET` request to the `/api/private-scoped` endpoint using the same token as above. You should see a `403 Forbidden` response, as this token does not possess the `read:messages` scope.

Back in the Auth0 Dashboard:

1. Go to the **Permissions** tab for the API you created above.
2. Add a permission of `read:messages` and provide a description.
3. Go to the **Machine to Machine Applications** tab.
4. Expand your authorized test application, select the `read:messages` scope, then click **UPDATE** and then **CONTINUE**.
5. Click the **Test** tab, then **COPY TOKEN**.

Issue a GET request to `/api/private-scoped`, this time passing the token you obtained above (with the `read:messages` scope) as an `Authorization` header set to `Bearer YOUR-API-TOKEN-HERE`. You should see the response:

```json
{"message":"All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope"}
```

## Additional Information

- [Spring 5 API Quickstart](https://auth0.com/docs/quickstart/backend/java-spring-security5)
- [Spring Security WebFlux Resrouce Server Documentation](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#webflux-oauth2-resource-server)

# What is Auth0?

Auth0 helps you to:

- Add authentication with [multiple authentication sources](https://docs.auth0.com/identityproviders), either social like **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, among others**, or enterprise identity systems like **Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider**.
- Add authentication through more traditional **[username/password databases](https://docs.auth0.com/mysql-connection-tutorial)**.
- Add support for **[linking different user accounts](https://docs.auth0.com/link-accounts)** with the same user.
- Support for generating signed [Json Web Tokens](https://docs.auth0.com/jwt) to call your APIs and **flow the user identity** securely.
- Analytics of how, when and where users are logging in.
- Pull data from other sources and add it to the user profile, through [JavaScript rules](https://docs.auth0.com/rules).

## Create a Free Auth0 Account

1. Go to [Auth0](https://auth0.com/signup) and click Sign Up.
2. Use Google, GitHub or Microsoft Account to login.

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section. Please do not report security vulnerabilities on the public GitHub issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

## Author

[Auth0](https://auth0.com)

## License

This project is licensed under the MIT license. See the [LICENSE](../LICENSE) file for more info.