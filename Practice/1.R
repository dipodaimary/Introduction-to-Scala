churn_data_tbl_train$Churn<-as.factor(churn_data_tbl_train$Churn)
library(caret)
library(rpart.plot)
trctrl <- trainControl(method = "repeatedcv", number = 10, repeats = 3)
set.seed(3333)
dtree_fit <- train(Churn ~ ltv+discounts+visits+lineitems+discounts+abs+bills, data = churn_data_tbl_train, method = "rpart",
                   parms = list(split = "information"),
                   trControl=trctrl,
                   tuneLength = 10)


pdf('/tmp/out.pdf')
prp(dtree_fit$finalModel, box.palette = c("Blue","Brown"), tweak = 1.2)
dev.off()

churn_data_tbl_train<-subset(churn_data_tbl_train,(!is.null(latency) & latency>0))
trctrl <- trainControl(method = "repeatedcv", number = 10, repeats = 3)
set.seed(3333)
dtree_fit <- train(Churn ~ ltv+discounts+visits+lineitems+latency+discounts+abs+bills, data = churn_data_tbl_train, method = "rpart",
                   parms = list(split = "information"),
                   trControl=trctrl,
                   tuneLength = 10)


pdf('/tmp/out.pdf')
prp(dtree_fit$finalModel, box.palette = c("Blue","Brown"), tweak = 1.2)
dev.off()
