export function Diet(number, _name, dietType, calories, mealsNumber, price,
    modifiedBy, createdBy, modificationDate, creationDate) {

  this.number = number;
  this._name = _name;
  this.dietType = dietType;
  this.calories = calories;
  this.mealsNumber = mealsNumber;
  this.price = price;
  this.modifiedBy = modifiedBy;
  this.createdBy = createdBy;
  this.creationDate = creationDate;
  this.modificationDate = modificationDate;
}