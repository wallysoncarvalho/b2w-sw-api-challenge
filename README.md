# Challenge Backend - Star Wars - B2W

## Application

This project is a simple REST API that makes a few operations (described further) on the resource `planet`, that is,
Star Wars planets.

When creating a planet, this service communicates with `SWAPI` so it can query if `SWAPI` has the newly registered
planet and, if so, on how many movies does this planet appears.

This API was implemented with JAVA 11, using Spring Boot and `Mongo DB starter` to handle persistence with Mongo DB.

The package `planet` is responsible to maintain the objects that handles the operations related to the planet. These
operations are exposed through the class `PlanetComponent`.

The web layer is located on the package `web` where it controls all incoming http request related to the planet. The
controller invokes the appropriate behavior that `PlanetComponent` exposes depending on the kind of request that was
made.

## Tests

As we do not have many business rules on this problem I opted to use the integration approach for the tests with the
help of `spring-boot-test` to set up the environment and provide Junit 5 (among others). Also, on the persistence side,
I use `flapdoodle` as the test database.

## Run

### Docker

The preferred way to run this application is by using `docker-compose` because it already sets up everything
automatically.

If **docker and docker-compose** is available, one can simply run `docker-compose up -d` at the root of the project. The
image of the API will be built by the `Dockerfile`, note that it also uses a multi-stage build, where the first stage is
to generate the fat jar of the application and create a small image using the `layered jars` technique provided by
spring boot.

Also, keep in mind that this stage will need to download the dependencies to create the jar, so it may take a few
minutes. If you want to prevent this, you can build the jar with `mvn package -DskipTests=true` and comment the lines 4,
5 and 6 of the `Dockerfile`. For this you'll need Java 11 (with JAVA_HOME set) and can use the maven wrappers provided
on the project root (`mvnw` and `mvnw.cmd`).

## REST API

A POSTMAN collection named `B2W-BACKEND-CHEALLENGE.postman_collection.json` is provided at the root of this project, so
you can import it and easily call the services of the API.

***Get all planets***
----
Get all planets, paginated and ordered by the planet's name.

* **URL**

  /api/planet

* **Method:**

  `GET`

* **URL Params**

  **Not required:**

  `page=[integer] - page >= 1`

  `pageSize=[integer] - pageSize >= 5 or <= 20`

* **Success Response:**

    * **Code:** 200 <br />
      **
      Content:** `{ items : [{id: <string>, name: <string>, climate: <string>, terrain: <string>, movieAppearances: <integer>},...], total : 100 }`

* **Error Response:**

    * **Code:** 400 BAD REQUEST <br />
      **Cause:** When inserting invalid params <br />
      **Content:** `{ status: <integer>, message : <string> }`

***Register a new planet***
----
Register a new planet.

* **URL**

  /api/planet

* **Method:**

  `POST`

* **Body**

  **Required:**

  `[{name: <string>, climate: <string>, terrain: <string>}`

* **Success Response:**

    * **Code:** 201 <br />

* **Error Response:**

    * **Code:** 400 BAD REQUEST <br />
      **Cause:** When inserting invalid body or trying to create a planet with an existing name. <br />
      **Content:** `{ status: <integer>, message : <string> }`

***Get a planet by it's ID***
----
Get a planet by id

* **URL**

  /api/planet/:id

* **Method:**

  `GET`

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** `{id: <string>, name: <string>, climate: <string>, terrain: <string>, movieAppearances: <integer>}`

* **Error Response:**

    * **Code:** 400 BAD REQUEST <br />
      **Cause:** Id that do not exist <br />
      **Content:** `{ status: <integer>, message : <string> }`

***Delete a planet by it's ID***
----
Delete/remove a planet by it's ID.

* **URL**

  /api/planet/:id

* **Method:**

  `DELETE`

* **Success Response:**

    * **Code:** 204 <br />

***Get a planet by it's name***
----
Get a planet by id

* **URL**

  /api/planet/name/:name

* **Method:**

  `GET`

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** `{id: <string>, name: <string>, climate: <string>, terrain: <string>, movieAppearances: <integer>}`

* **Error Response:**

    * **Code:** 400 BAD REQUEST <br />
      **Cause:** Planet with provided name do no exist <br />
      **Content:** `{ status: <integer>, message : <string> }`
