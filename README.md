# Problem Statement
The goal of this capstone project is to demonstrate your mastery of Docker by designing, containerizing, and deploying a microservices-based application. You will be expected to apply Docker best practices, use Docker Compose for orchestration, and deploy your application on a cloud platform or a local Kubernetes cluster.
## Container 1 (Microservice 1)
The role of the first container is to be able to store the files to a persistent volume in GKE and serve as a gatekeeper to calculate the products from the stored file. It must:  
1. Be deployed as a service in GKE to be able to communicate with the Internet.
2. Have access to persistent volume in GKE to store and retrieve files.
3. Be able to communicate with container 2 and vice versa.
4. Validate the input JSON request incoming from the REST API.
5. Send the “file” parameter to container 2 to calculate the product from the “product” parameter in the API and return the response from container 2.
   
### Create a POST API with endpoint: /store-file
This API expects container 1 to create a file and store the data given in the API request. The file should be stored in the GKE persistent storage. You must make sure that your container can access the persistent storage.
**JSON Input:**  
{  
  “file”: “file.dat”,  
  “data”: “product, amount \nwheat, 10\nwheat, 20\noats, 5”  
}  
**JSON Output:**  
If the file is stored successfully, this message should be returned:  
{  
  “file”: “file.dat”,  
  “message”: “Success.”  
}  
For example, file.dat should look like:  
product, amount  
wheat,10  
wheat,20  
oats,5

If there was an error to store the file in the persistent storage, this message should be returned:  
{  
  “file”: “file.dat”,  
  “error”: “Error while storing the file to the storage.”  
}

If the file name is not provided, an error message is returned:  
{  
  “file”: null,  
  “error”: “Invalid JSON input.”  
}

### Create a POST API with endpoint: /calculate
If the file exists in persistent storage, the total of product is returned. The total is calculated by container 2

**JSON Input:**  
{  
  “file”: “file.dat”,  
  “product”: “wheat”  
}

**JSON Output:**  
The return response of /calculate API should be:  
{  
  “file”: “file.dat”,  
  “sum”: 30  
}

If a filename is provided, but the file contents cannot be parsed due to not following the CSV format described above, this message is returned:  
{  
  “file”: “file.dat”,  
  “error”: “Input file not in CSV format.”  
}

If the filename is provided, but not found in the persistent disk volume, this message is returned:  
{  
  “file”: “file.dat”,  
  “error”: “File not found.”  
}

If the file name is not provided, an error message is returned:  
{  
  “file”: null,  
  “error”: “Invalid JSON input.”  
}

