export function Account(login, email, password, active, confirmed, firstName,
    lastName, phoneNumber, language, modifiedBy, lastKnownGoodLogin,
    lastKnownGoodLoginIp, lastKnownBadLogin, lastKnownBadLoginIp,
    badLoginsCounter, loyaltyFactor, gymMember, createdBy, creationDate,
    modificationDate, accessLevels) {

  this.login = login;
  this.email = email;
  this.password = password;
  this.active = active;
  this.confirmed = confirmed;
  this.firstName = firstName;
  this.lastName = lastName;
  this.phoneNumber = phoneNumber;
  this.language = language;
  this.loyaltyFactor = loyaltyFactor;
  this.gymMember = gymMember;
  this.lastKnownGoodLogin = lastKnownGoodLogin;
  this.lastKnownGoodLoginIp = lastKnownGoodLoginIp;
  this.lastKnownBadLogin = lastKnownBadLogin;
  this.lastKnownBadLoginIp = lastKnownBadLoginIp;
  this.badLoginsCounter = badLoginsCounter;
  this.createdBy = createdBy;
  this.modifiedBy = modifiedBy;
  this.creationDate = creationDate;
  this.modificationDate = modificationDate;
  this.accessLevels = accessLevels;
}