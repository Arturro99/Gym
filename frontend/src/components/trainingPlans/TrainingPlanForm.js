import Form from "../common/Form";
import Joi from "joi";
import Dropdown from "../common/Dropdown";
import { TrainingType } from "../../model/TrainingType"
import { withTranslation } from "react-i18next";
import {
  createTrainingPlan,
  getTrainingTypes
} from "../../services/TrainingPlanService";

class TrainingPlanForm extends Form {

  state = {
    data: {
      trainingPlanNumber: '',
      title: '',
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
    title: Joi.string().required(),
    trainingType: Joi.any().required(),
    personalTrainingsNumber: Joi.number().min(0).required(),
    trainer: Joi.string().required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    trainingPlanNumber: this.fieldRestrictions.trainingPlanNumber,
    title: this.fieldRestrictions.title,
    trainingType: this.fieldRestrictions.trainingType,
    personalTrainingsNumber: this.fieldRestrictions.personalTrainingsNumber,
    trainer: this.fieldRestrictions.trainer,
    price: this.fieldRestrictions.price
  })

  async componentDidMount() {
    let trainingTypes = await getTrainingTypes();
    trainingTypes = trainingTypes.map(type => new TrainingType(type.title));
    let data = this.state.data;
    data.trainingType = trainingTypes[0].name;
    this.setState({
      data: data,
      trainingTypes: trainingTypes
    });
  }

  continueSubmitting = async () => {
    const { t } = this.props;
    const {
      trainingPlanNumber,
      title,
      trainingType,
      personalTrainingsNumber,
      trainer,
      price
    } = this.state.data;

    await createTrainingPlan({
      trainingPlanNumber: trainingPlanNumber,
      title: title,
      trainingType: trainingType.toUpperCase(),
      personalTrainingsNumber: parseInt(personalTrainingsNumber),
      trainer: trainer,
      price: parseFloat(price)
    }, t).then(resp => {
      if (resp && resp.status === 201) {
        this.props.history.push('/trainingPlans');
      }
    });
  }

  handleTrainingTypeChange = (type) => {
    let state = { ...this.state };
    state.data.trainingType = type;
    this.setState({ state });
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card-header mt-5 w-50 mx-auto">
          <h1 className="text-center">{t('newTrainingPlan')}</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("trainingPlanNumber", t("number"))}
            {this.renderInput("title", t("name"))}
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