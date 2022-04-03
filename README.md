# xml-processor
Small project for testing marshalling/unmarshalling XML to POJO, validating via xsd using maven plugins for automation

```
Usage:
 java -jar xml-processor.jar -h
 java -jar xml-processor.jar -v
 java -jar xml-processor.jar <path_1> [... <path_n>]

 -h,--help     Show this help message
 -v,--version  Show program version
 <path_x>      absolute or relative path to xml file or folder containing xml files for validating

 Example:  java -jar xml-processor.jar /home/user/xml-files-folder ../xml-file.xml
```

TODO: clean the code, maven optimizations, write tests etc etc etc