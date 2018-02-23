exports.handler = (event, context, callback) => {
    const AWS = require('aws-sdk');

    const s3Client  = new AWS.S3({
        apiVersion: '2006-03-01',
        accessKeyId: process.env.ACCESS_KEY_ID,
        secretAccessKey: process.env.SECRET_ACCESS_KEY
    });

    const sourceBucket = process.env.BUCKET_FOOTAGE_SAMPLES;
    const footageFile = process.env.FOOTAGE_FILE;
    const destinationBucket = process.env.BUCKET_FOOTAGE_UPLOADS;
    const userUUID = process.env.FAKE_USER_UUID;
    const timestamp = new Date().getTime();

    const params = {
        Bucket: destinationBucket,
        CopySource: `${sourceBucket}/${footageFile}`,
        Key: `${userUUID}/${timestamp}/${footageFile}`
    }

    s3Client.copyObject(params, (err, data) => {
        if (err) console.error(err, err.stack);

else console.log(data);
});

    callback(null, 'Hello from Lambda');
};