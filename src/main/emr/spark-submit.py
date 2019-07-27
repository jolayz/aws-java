from pyspark.sql import SparkSession
spark = SparkSession.builder.appName('demo').getOrCreate()

dataset = spark.read.parquet("s3://amazon-reviews-pds/parquet/product_category=Watches/")

dataset.filter("star_rating == 5").sort(dataset.helpful_votes.desc())).show(10)