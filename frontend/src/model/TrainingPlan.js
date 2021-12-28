export function TrainingPlan(number, title, trainingType,
    personalTrainingsNumber, trainer, price, modifiedBy, createdBy,
    modificationDate, creationDate) {

  this.number = number;
  this.title = title;
  this.trainingType = trainingType;
  this.personalTrainingsNumber = personalTrainingsNumber;
  this.trainer = trainer;
  this.price = price;
  this.modifiedBy = modifiedBy;
  this.createdBy = createdBy;
  this.creationDate = creationDate;
  this.modificationDate = modificationDate;
}