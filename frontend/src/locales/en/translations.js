export const TRANSLATIONS_EN = {
  welcome: 'Welcome to the Gym',

  //Common
  signIn: 'Sign in',
  signOut: 'Sign out',
  cancel: 'Cancel',
  number: 'Number',
  name: 'Name',
  trainer: 'Trainer',
  admin: 'Admin',
  client: 'Client',
  activity: 'Activity',
  active: 'Active',
  inactive: 'Inactive',
  actions: 'Actions',
  create: 'Create',
  price: 'Price',
  delete: 'Delete',
  update: 'Update',
  apply: 'Apply',
  activate: 'Activate',
  deactivate: 'Deactivate',
  details: 'Details',
  creationDate: 'Creation Date',
  modificationDate: 'Modification Date',
  createdBy: 'Created by',
  modifiedBy: 'Modified by',
  save: 'Save',
  yes: 'Yes',
  no: 'No',

  error_title_string_empty: 'Name required',
  error_trainer_string_empty: 'Trainer required',

  //Registration form
  login: 'Login',
  email: 'E-mail',
  password: 'Password',
  repeatedPassword: 'Repeat password',
  firstName: 'First name',
  lastName: 'Last name',
  phoneNumber: 'Phone number',
  register: 'Register',

  error_password_string_pattern_base: 'Password must match some constraints',
  error_repeatedPassword_any_only: 'Passwords do not match',
  error_login_string_alphanum: 'Login must contain only alphanumeric symbols',
  error_email_string_email: 'Not a valid email',
  error_phoneNumber_string_min: 'Phone number must have at least 9 digits',
  error_phoneNumber_string_max: 'Phone number must have at most 12 digits',
  error_login_string_empty: 'Login required',
  error_email_string_empty: 'Email required',
  error_password_string_empty: 'Password required',
  error_repeatedPassword_string_empty: 'You need to repeat your password',
  error_firstName_string_empty: 'First name required',
  error_lastName_string_empty: 'Last name required',

  //Activities
  noActivities: 'No available activities. Try again later...',
  newActivity: 'Add activity',
  updateActivity: 'Update activity',
  startDate: 'Start date',
  duration: 'Duration',
  capacity: 'Capacity',
  chooseDateTime: 'Choose date and time',
  activities: 'Activities',
  activityDetails: 'Activity details',

  error_activityNumber_string_pattern_base: 'Number must match: \'ACTXXX\' where XXX are digits',
  error_activityNumber_string_empty: 'Number required',
  error_duration_number_base: 'Duration required',
  error_duration_number_min: 'Duration must be at least 30',
  error_capacity_number_base: 'Capacity required',
  error_capacity_number_min: 'Capacity must be at least 1',

  activity_number_conflict: 'Activity with such number exists',
  activity_remove_conflict: 'Activity is being used',
  activity_notFound: 'Activity already removed',
  activity_update_success: 'Activity updated successfully',
  activity_add_success: 'Activity added successfully',
  activity_remove_success: 'Activity removed successfully',
  activity_inappropriate_date: 'Date should be a value from future',

  //Diets
  noDiets: 'No available diets. Try again later...',
  noIndividualDiets: 'You have no assigned diets. It is high time you tried something new!',
  newDiet: 'Add diet',
  updateDiet: 'Update diet',
  calories: 'Amount of calories',
  mealsNumber: 'Number of meals',
  diets: 'Diets',
  dietType: 'Diet type',
  dietDetails: 'Diet details',

  error_dietNumber_string_pattern_base: 'Number must match: \'DIEXXX\' where XXX are digits',
  error_dietNumber_string_empty: 'Number required',
  error_calories_number_base: 'Amount of calories required',
  error_calories_number_min: 'Amount of calories must be at least 100',
  error_mealsNumber_number_base: 'Meals number required',
  error_mealsNumber_number_min: 'Capacity must be at least 1',
  error_price_number_base: 'Price required',
  error_price_number_min: 'Price must be at least 1',

  diet_number_conflict: 'Diet with such number exists',
  diet_possessing_conflict: 'You already own this diet',
  diet_remove_conflict: 'Diet is being used',
  diet_notFound: 'Diet already removed',
  diet_update_success: 'Diet updated successfully',
  diet_add_success: 'Diet added successfully',
  diet_remove_success: 'Diet removed successfully',

  //Training plans
  noTrainingPlans: 'No available training plans. Try again later...',
  noIndividualTrainingPlans: 'You have no assigned training plans. It is high time you tried something new!',
  newTrainingPlan: 'Add training plan',
  personalTrainingsNumber: 'Number of personal trainings',
  trainingPlans: 'Training plans',
  trainingType: 'Training type',
  trainingPlanDetails: 'Training plan details',

  error_trainingPlanNumber_string_pattern_base: 'Number must match: \'TRAXXX\' where XXX are digits',
  error_trainingPlanNumber_string_empty: 'Number required',
  error_personalTrainingsNumber_number_base: 'Number of personal trainings required',
  error_personalTrainingsNumber_number_min: 'Number of personal trainings must be positive',


  trainingPlan_number_conflict: 'Training plan with such number exists',
  trainingPlan_trainer_conflict: 'You run this training plan',
  trainingPlan_possessing_conflict: 'You already own this training plan',
  trainingPlan_remove_conflict: 'Training plan is being used',
  trainingPlan_notFound: 'Training plan already removed',
  trainingPlan_update_success: 'Training plan updated successfully',
  trainingPlan_add_success: 'Training plan added successfully',
  trainingPlan_remove_success: 'Training plan removed successfully',

  //Account
  accounts: 'Accounts',
  myAccount: 'My account',
  updateAccount: 'Update account',
  accountDetails: 'Account details',
  myTrainingPlans: 'My training plans',
  myDiets: 'My diets',
  confirmed: 'Confirmed',
  unconfirmed: 'Unconfirmed',
  status: 'Status',
  gymMember: 'Gym member',
  accessLevels: 'Access levels',
  language: 'Language',
  loyaltyFactor: 'Loyalty factor',
  lastKnownGoodLogin: 'Last known good login',
  lastKnownGoodLoginIp: 'Last known good login IP',
  lastKnownBadLogin: 'Last known bad login',
  lastKnownBadLoginIp: 'Last known bad login IP',
  badLoginsCounter: 'Number of bad logins',

  account_login_conflict: 'Login with such email or login exists',
  account_update_success: 'Account updated successfully',
  account_notFound: 'Can\'t find provided account',

  //Booking
  bookings: 'Bookings',
  bookingDetails: 'Booking details',
  myBookings: 'My bookings',
  completed: 'Completed',
  complete: 'Complete',
  incomplete: 'Incomplete',
  state: 'State',
  pending: 'Pending',
  non_pending: 'Enrolled',
  updateBooking: 'Update booking',

  booking_add_success: 'A place for activity booked successfully',
  booking_update_success: 'Booking updated successfully',
  booking_cancel_success: 'Booking cancelled successfully',
  booking_complete_success: 'Booking completed successfully',
  booking_possessing_conflict: 'You have already booked a place for this activity',
  booking_cancellation_deadline_conflict: 'You can no longer cancel this booking',
  booking_completion_conflict: 'Cannot complete booking before the start of activity',

  //Access levels
  assignAccessLevels: 'Assign access levels',

  accessLevel_inappropriate_trainer: 'Provided user should have a trainer role',
  accessLevel_inappropriate_client: 'Provided user should have a trainer role',

};