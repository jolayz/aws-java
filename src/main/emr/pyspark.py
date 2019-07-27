dataset = spark.read.parquet("s3://amazon-reviews-pds/parquet/product_category=Watches/")

dataset.printSchema

dataset.take(10).iterator

dataset.filter("star_rating == 5").show(10)

dataset.filter("star_rating == 5").sort(desc("helpful_votes")).show(10)