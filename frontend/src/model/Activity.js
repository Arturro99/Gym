export function Activity(number, _name, startDate, duration, trainer, active,
    capacity, modifiedBy, createdBy, modificationDate, creationDate) {

    this.number = number;
    this._name = _name;
    this.startDate = startDate;
    this.duration = duration;
    this.trainer = trainer;
    this.active = active;
    this.capacity = capacity;
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.creationDate = creationDate;
    this.modificationDate = modificationDate;
}