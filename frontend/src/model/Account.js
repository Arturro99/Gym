export function Account(login, email, active, confirmed, firstName,
    lastName, phoneNumber, language, loyaltyFactor, gymMember,
    badLoginsCounter, modifiedBy, creationDate,
    modificationDate) {

  this.login = login;
  this.email = email;
  this.active = active;
  this.confirmed = confirmed;
  this.firstName = firstName;
  this.lastName = lastName;
  this.phoneNumber = phoneNumber;
  this.language = language;
  this.loyaltyFactor = loyaltyFactor;
  this.gymMember = gymMember;
  // this.lastKnownGoodLogin = lastKnownGoodLogin;
  // this.lastKnownGoodLoginIp = lastKnownGoodLoginIp;
  // this.lastKnownBadLogin = lastKnownBadLogin;
  // this.lastKnownBadLoginIp = lastKnownBadLoginIp;
  this.badLoginsCounter = badLoginsCounter;
  this.modifiedBy = modifiedBy;
  this.creationDate = creationDate;
  this.modificationDate = modificationDate;
}