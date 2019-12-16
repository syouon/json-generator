# JSON-GENERATOR
Tool to generate random JSON, including random field names (without meaning) and values, with a specified number of object and fields.

## Getting started

### Build

First, clone the repository and go into the project.
To build the project, use the Gradle wrapper script using the following command:
```
./gradlew build
```

Note: It might take some time on the first execution because of Gradle download.

### Execute

Then, run the tool executing this command:
```
./gradlew run --args="[NUMBER_OF_JSON] [NUMBER_OF_FIELDS] [KEEP_SAME_FIELDS] [OUTPUT_FILE]"
```
* **NUMBER_OF_JSON**: total number of wanted JSON object in the array, **default**: 1
* **NUMBER_OF_FIELDS**: number of fields for each JSON object, **default**: 10
* **KEEP_SAME_FIELDS**: true if you want all JSON objects to have same fields, false otherwise. **Default**: false
* **OUTPUT_FILE**: file where generated JSON will be written, **default**: standard output

All parameters have default values. So you can omit some parameters.

Here are other possible uses:
```
./gradlew run --args="[NUMBER_OF_JSON]"
./gradlew run --args="[NUMBER_OF_JSON] [NUMBER_OF_FIELDS]"
./gradlew run --args="[NUMBER_OF_JSON] [NUMBER_OF_FIELDS] [KEEP_SAME_FIELDS]"
```

In order to display only generated JSON (if using standard output), use the `-q` option:
```
./gradlew run -q --args="2 5 false output.json"
```

**Examples:**
* To generate 1 JSON object with 10 fields, displayed in standard output:
```
./gradlew run -q
```

* To generate 5 JSON object with 10 fields each, displayed in standard output:
```
./gradlew run -q --args="5"
```

* To generate 1 JSON object with 5 fields, displayed in standard output:
```
./gradlew run -q --args="1 5"
```

* To generate 2 JSON object with 5 **same** fields each, writing result in `output.json` file`:
```
./gradlew run -q --args="2 5 true output.json"
```

### Tips

To format the generated JSON in the standard output, you can use `jq` command line:
```
./gradlew run -q --args="100 20" | jq '.'
``` 
To format the generated JSON in the standard output with each JSON object in one line:
```
./gradlew run -q --args="5 2" | jq -c '.[]'
```
