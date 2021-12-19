export function Booking(number, account, activity,
    active, completed, pending, modifiedBy, createdBy,
    modificationDate, creationDate) {

  this.number = number;
  this.account = account;
  this.activity = activity;
  this.active = active;
  this.completed = completed;
  this.pending = pending;
  this.modifiedBy = modifiedBy;
  this.createdBy = createdBy;
  this.creationDate = creationDate;
  this.modificationDate = modificationDate;
}