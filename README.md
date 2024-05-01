# Problem Statement
Build two simple microservices in any programming language. These services should be able to interact with each other. Deploy the services on GCP, by creating a CI/CD pipeline which deploys the service to GKE. In GKE, create a persistent volume that should be accessed by both containers to store and retrieve file data. You can use GCP services such as Cloud Source Repository (a tool similar to Gitlab) to store the source code, Artifact Registry (a tool similar to Docker Hub) to store the Docker images, and GKE to deploy the images to clusters.

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

## Container 2 (Microservice 2)
The role of container 2 is to listen on another port and endpoint that you define for the service and returns the total of the product. It must:
1. Have access to the persistent volume of GKE.
2. Container 2 should be able to interact with container 1 and vice versa.
3. Calculate the total of the product by calculating the sum of rows of the same product from the
given file.
4. Return the total in the appropriate JSON format, or an error indicating the file is not a proper CSV file in the appropriate JSON format.

**JSON Output:**  
{  
  “file”: “file.dat”,  
  “sum”: 30  
}

## Additional Requirements
1. You must create two repositories for each container in GCP Cloud Source Repository and use GCP Cloud Build to build the pipelines for both services. You must make sure the CI/CD pipeline works well such that the application deployed in the GKE cluster is updated when new code commits check in.
2. You will have to learn the GCP CI/CD tools by yourselves. The tools are Cloud Source Repository, Cloud Build, and Artifact Registry.
3. You will have to learn Terraform by yourselves and create a Terraform script to start your GKE cluster by running the script. You will be launching your GKE cluster by running the Terraform script from GCP Cloud Shell. 
4. You must create a yaml file “xxxx.yaml” to deploy your application (workload) to the GKE cluster from the Cloud Shell.

