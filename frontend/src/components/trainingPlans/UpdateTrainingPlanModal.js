import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import Dropdown from "../common/Dropdown";
import Joi from "joi";
import { TrainingType } from "../../model/TrainingType";
import {
  getTrainingPlan,
  updateTrainingPlan
} from "../../services/TrainingPlanService";

class UpdateTrainingPlanModal extends Form {

  state = {
    data: {
      title: '',
      trainingType: '',
      personalTrainingsNumber: '',
      price: ''
    },
    trainingTypes: [],
    modalId: 'updateTrainingPlanModal',
    name: 'updateTrainingPlan',
    errors: []
  }

  fieldRestrictions = {
    title: Joi.string().required(),
    trainingType: Joi.any().required(),
    personalTrainingsNumber: Joi.number().min(0).required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    title: this.fieldRestrictions.title,
    trainingType: this.fieldRestrictions.trainingType,
    personalTrainingsNumber: this.fieldRestrictions.personalTrainingsNumber,
    price: this.fieldRestrictions.price
  })

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('show.bs.modal', () => {
      this.resetData();
    })

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

  handleTrainingTypeChange = (type) => {
    let state = { ...this.state };
    state.data.trainingType = type;
    this.setState({ state });
  }

  continueSubmitting = async () => {
    const {
      title,
      personalTrainingsNumber,
      price,
      trainingType,
      number
    } = this.props.trainingPlan;
    const { t } = this.props;
    let newTitle, newPersonalTrainingsNumber, newPrice, newTrainingType = null;

    if (title !== this.state.data.title) {
      newTitle = this.state.data.title;
    }
    if (trainingType !== this.state.data.trainingType) {
      newTrainingType = this.state.data.trainingType.toUpperCase();
    }
    if (personalTrainingsNumber !== this.state.data.personalTrainingsNumber) {
      newPersonalTrainingsNumber = this.state.data.personalTrainingsNumber;
    }
    if (price !== this.state.data.price) {
      newPrice = this.state.data.price;
    }

    await updateTrainingPlan({
      number: number,
      title: newTitle,
      trainingType: newTrainingType,
      personalTrainingsNumber: newPersonalTrainingsNumber,
      price: newPrice
    }, t);
  }

  async resetData() {
    let currentData = { ...this.state.data };
    const trainingPlan = await getTrainingPlan(this.props.trainingPlan.number);
    currentData.title = trainingPlan.title;
    currentData.trainingType = trainingPlan.trainingType;
    currentData.personalTrainingsNumber = trainingPlan.personalTrainingsNumber;
    currentData.price = trainingPlan.price;
    this.setState({ data: currentData });
  }

  render() {
    const { modalId, name, data } = this.state;
    const { t } = this.props;
    return (
        <div className="modal fade" id={this.state.modalId} tabIndex="-1"
             role="dialog" aria-labelledby="exampleModalCenterTitle"
             aria-hidden="true">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content bg-secondary">
              <div className="modal-header text-light">
                <h1 className="modal-title position-relative start-50 translate-middle-x">
                  {t(name)}</h1>
                <button type="button" className="btn-close btn-light"
                        data-bs-dismiss="modal" aria-label="Close"/>
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('title', t('name'), 'text',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.title)}
              </div>
              <Dropdown items={this.state.trainingTypes}
                        itemName={t('trainingType')}
                        propertyName='name'
                        buttonLabel={this.state.data.trainingType}
                        onChangeBtn={this.handleTrainingTypeChange}
                        labelClassName={"text-light"}
                        style={{ left: 0 }}/>
              <div className="modal-body ms-4">
                {this.renderInput('personalTrainingsNumber',
                    t('personalTrainingsNumber'), 'number',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.personalTrainingsNumber)}
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('price', t('price'), 'number',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.price, this.state.data.price === t('yes'),
                    this.handleCheck)}
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary"
                        data-bs-toggle='modal'
                        data-bs-target={`#${modalId}`}
                        onClick={this.handleSubmit}
                        disabled={this.validate()}>
                  {t('save')}</button>
                <button type="button" className="btn btn-secondary"
                        data-bs-toggle='modal'
                        data-bs-target={`#${modalId}`}>
                  {t('cancel')}</button>
              </div>
            </div>
          </div>
        </div>
    )
  }
}

export default withTranslation()(UpdateTrainingPlanModal);