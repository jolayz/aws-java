// Set up AWS client
var AWS = require('aws-sdk');
var dynamodb = new AWS.DynamoDB();

// update AWS configuration to set region
AWS.config.update({region : 'us-west-2'});

exports.handler = function(event, context) {
    // Keep track of how many requests are in flight
    var inflightRequests = 0;

    event.Records.forEach(function(record) {
        console.log('DynamoDB Record: %j', record.dynamodb);
        // Get the new image of the DynamoDB Streams record
        var newItemImage = record.dynamodb.NewImage;

        // Set the appropriate parameters for UpdateItem
        // Refer to the ADD operation in the UpdateItem API for UpdateExpression
        // http://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_UpdateItem.html
        // Adds the specified value to the item, if attribute does not exist, set the attribute
        var updateItemParams = {
            TableName: "GameScoresByUser",
            Key : { Username : newItemImage.Username },
            UpdateExpression : 'ADD Score :attrValue',
            ExpressionAttributeValues : {':attrValue' : newItemImage.Score}
        }

        // Make a callback function to execute once UpdateItem request completes
        // It may be helpful to refer to the updateItem method for the Javascript SDK
        // http://docs.aws.amazon.com/AWSJavaScriptSDK/latest/AWS/DynamoDB.html\#updateItem-property
        var updateItemCallback = function(err, data) {
            if (err) {
                // log errors
                console.log(err, err.stack);
            } else {
                // check if all requests are finished, if so, end the function
                inflightRequests--;
                if (inflightRequests === 0) {
                    context.succeed("Successfully processed " + event.Records.length + " records.");
                }
            }
        };

        // Send UpdateItem request to DynamoDB
        dynamodb.updateItem(updateItemParams, updateItemCallback);
        // Increase count for number of requests in flight
        inflightRequests++;
        });

        // If there are no more requests pending, end the function
        if (inflightRequests === 0) {
            context.succeed("Successfully processed " + event.Records.length + " records.");
        }
    };