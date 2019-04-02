# Weather clothing advisor assignment

We want to creat a startup to give our customers advice depending on some weather conditions. The first product we want
to launch is an API that gives our customer a valuable advice on what to wear depending on the temperature.

## Requirements

As a really technical Product Owner I would like to have the following:

* We want to store the relationship between temperature and clothing in an structured way. Therefore you should store it
in a relational database (it can be an in-memory database). We want the following to be stored:

  - [-10,0]: Russian coat
  - [1, 10]: US Parka
  - [11, 20]: Regular Jacket
  - [21, 30]: T-shirt and flip-flops
  - [31, 40]: Swimming costume

Please notice that the ranges are inclusive.

* A REST API to manage the stored data. That means:

  - A POST method to add new pair of (ranges, advice)
  - A PUT method to update an existing advice related to a range
  - A GET method to retrieve the list of all pairs (ranges, advice)
  - A GET method to retrieve a specific pair of (range, advice)
  - A DELETE method to remove a specific pair of (range, advice)

This API is intended for an internal use (for our CEO to manage it).

* A REST API to give the advice to the customer. That means:

  - A GET method that will provide the city name, current temperature and the right advice. This endpoint should expect
   a city name as a parameter. The content type should be JSON and the following is an expected payload result:
  
```json
{
  "city": "Amsterdam",
  "currentTemperature": 3,
  "advice": "US Parka"
}
```

For getting the weather information we need to call an external service API. Our company is evaluating working with 
[openweathermap][1] external service. But, it's up to you to use any other weather service as long as it's via HTTP API
calls.

[1]: https://openweathermap.org/api

## Acceptance criteria

I'm a really strict PO so I would not consider this story ok unless it meets the following criteria:

* All provided tests should be passing.

You see? I'm not asking that much ;)

Btw, you are free to modify any resource in this assignment the way you like.
