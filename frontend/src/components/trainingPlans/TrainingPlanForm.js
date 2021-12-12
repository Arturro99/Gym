import Form from "../common/Form";
import Joi from "joi";
// import 'bootstrap/dist/js/bootstrap.min.js';
import Dropdown from "../common/Dropdown";
import { TrainingType } from "../../model/TrainingType"
import { withTranslation } from "react-i18next";

class TrainingPlanForm extends Form {

  state = {
    data: {
      trainingPlanNumber: '',
      name: '',
      trainingType: '',
      personalTrainingsNumber: '',
      trainer: '',
      price: ''
    },
    errors: {},
    trainingTypes: []
  };

  fieldRestrictions = {
    trainingPlanNumber: Joi.string().pattern(/^TRA[0-9]{3}$/).required(),
    name: Joi.string().required(),
    trainingType: Joi.any().required(),
    personalTrainingsNumber: Joi.number().min(0).required(),
    trainer: Joi.string().required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    trainingPlanNumber: this.fieldRestrictions.trainingPlanNumber,
    name: this.fieldRestrictions.name,
    trainingType: this.fieldRestrictions.trainingType,
    personalTrainingsNumber: this.fieldRestrictions.personalTrainingsNumber,
    trainer: this.fieldRestrictions.trainer,
    price: this.fieldRestrictions.price
  })

  componentDidMount() {
    const type1 = new TrainingType('Strength');
    const type2 = new TrainingType('Cardio');
    const type3 = new TrainingType('Calisthenics');
    const type4 = new TrainingType('Crossfit');

    let mockedData = this.state.data;
    mockedData.trainingType = 'Strength';
    this.setState({
      data: mockedData,
      trainingTypes: [type1, type2, type3, type4]
    });
  }

  continueSubmitting = () => {
    console.log(
        `TrainingPlan: ${this.state.data.trainingPlanNumber} created ${this.state.data.trainingType}`)
  }

  handleTrainingTypeChange = (type) => {
    let state = { ...this.state };
    state.data.trainingType = type;
    this.setState({ state });
  }

  render() {
    const { t } = this.props;
    return (
        <div>
          <h1 className="modal-header">{t('newTrainingPlan')}</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("trainingPlanNumber", t("number"))}
            {this.renderInput("name", t("name"))}
            <Dropdown items={this.state.trainingTypes}
                      itemName={t('trainingType')}
                      propertyName='name'
                      buttonLabel={this.state.data.trainingType}
                      onChangeBtn={this.handleTrainingTypeChange}/>
            {this.renderInput("personalTrainingsNumber",
                t("personalTrainingsNumber"), 'number')}
            {this.renderInput("trainer", t("trainer"))}
            {this.renderInput("price", t("price"), 'number')}
            {this.renderButton(t("create"))}
          </form>
        </div>
    );
  }
}

export default withTranslation()(TrainingPlanForm);