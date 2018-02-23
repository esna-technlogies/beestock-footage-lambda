# Beestock Footage Lambda


### Description
This is a Java project that implements [AWS Lambda](https://docs.aws.amazon.com/lambda/latest/dg/welcome.html) function. 

The main goal of this lambda function is to run [AWS Transcoder](https://docs.aws.amazon.com/elastictranscoder/latest/developerguide/introduction.html) operations (jobs) on the footage files uploaded by the users of the [BeeStock](www.beesstock.com) website.  


### Lambda Function Settings on AWS
- **ARN**: `arn:aws:lambda:us-west-2:014519795457:function:footage-processor`
- **Url**: [footage-processor](https://us-west-2.console.aws.amazon.com/lambda/home?region=us-west-2#/functions/footage-processor)
- **Function Name**: `footage-processor`
- **Function Code**:
  - **Runtime**: `Java 8`
  - **Handler**: `com.beestock.FootageLambdaApp::handleRequest`
- **Environment Variables**:

  | Name | Description |
  |------|-------------|
  | ACCESS_KEY_ID | AWS Access Key ID |
  | SECRET_ACCESS_KEY | AWS Secret Access Key|
  | PIPELINE_ID | The id of the pipeline which is responsible of transcoding the footage file to different formats |
  | VALIDATION_PIPELINE_ID | The id of the pipeline which is responsible of validating the footage file |
  | REGION | AWS Region |
  | BUCKET_FOOTAGE_UPLOADS | The name of an S3 bucket that will trigger the lambda function to validate any uploaded footage |
  | BUCKET_FOOTAGE_VALIDATED | The name of an S3 bucket that will trigger the lambda function to transcode any validated footage |
  | BUCKET_UNAPPROVED_FILES | The name of an S3 bucket that will be used to store the converted formats of the footage file |
  | SUPPORTED_INPUT_FORMATS | A comma separated video formats that are supported by the lambda function to process
  | SUPPORTED_OUTPUT_FORMATS | A comma separated video formats to which transcoder will convert the validated footage file
  | WATERMARK_ID | A predefined ID by AWS Transcoder service, that identifies the position of the [watermark](https://docs.aws.amazon.com/elastictranscoder/latest/developerguide/watermarks.html) in the video window
  | WATERMARK_INPUT_KEY | The filename of the [watermark](https://docs.aws.amazon.com/elastictranscoder/latest/developerguide/watermarks.html) image used by AWS Transcoder pipeline |

 
- **Execution Role**: 
  - **Custom Role**: transcoding-lambda-role
  
- **Basic Settings**:
  - **Description**: This Lambda function handles transcoding operation on footage files uploaded by BeeStock' users
  - **Memory**: 256 MB
  - **Timeout**: 0min 30sec
  
- **Triggers**:
  - **S3**:
    
    **ARN**: `arn:aws:s3:::beestock-footage-validated`
    - **Bucket**: beestock-footage-validated
    - **Event Type**: Object Created (All)
    - **Enable Trigger**: true
    
    **ARN**: `arn:aws:s3:::beestock-footage-uploads`
    - **Bucket**: beestock-footage-uploads
    - **Event Type**: Object Created (All)
    - **Enable Trigger**: true
    

### Test The Project
1- Open [AWS Lambda - US-WEST-2](https://us-west-2.console.aws.amazon.com/lambda/home?region=us-west-2) console
2- Create a Lambda function with the following settings:
  - Select **Author From Scratch**
      - Name: simulate-footage-upload
      - Runtime: Node.js 6.10
      - Role: Choose an Existing role
      - Existing Role: Lambda_Basic_Execution
      
3- Open the Lambda function with the name **simulate-footage-upload**
  - Apply the following settings:
    - Function Code:
      - Code Entry Type: Edit code inline
      - Runtime: Node.js 6.10
      - Handler: index.handler
        
      - Copy the code from simulate-footage-uploading/index.js to index.js on the console
        
    - Environment Variables:
    
      | Name | Description |
      |------|-------------|
      | ACCESS_KEY_ID | AWS Access Key ID |
      | SECRET_ACCESS_KEY | AWS Secret Access Key|    
      | BUCKET_FOOTAGE_SAMPLES | The name of an S3 bucket that will hold samples of footage files |
      | BUCKET_FOOTAGE_UPLOADS | The name of an S3 bucket that will trigger the lambda function `footage-processor` to staring validating the uploaded footage file|
      | FAKE_USER_UUID | A user uuid that will be used to simulate a user uploading a footage file to his unique folder on BUCKET_FOOTAGE_UPLOADS |
      
    - Basic Settings:
      - Description: It simulates uploading a footage file by copying samples from BUCKET_FOOTAGE_SAMPLES to BUCKET_FOOTAGE_UPLOADS
      - Memory (MB): 128 MB
      - Timeout: 01min 00sec
    
    - Create Test Event:
      - Click **Select a test event**
      - Create any kind of tests (This lambda does not depend on external event)  
  
  - Click **Save** to save changes of this lambda function 
  - Click **Test** to run this lambda function