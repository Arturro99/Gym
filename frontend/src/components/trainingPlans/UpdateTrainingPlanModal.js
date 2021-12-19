import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import Dropdown from "../common/Dropdown";
import Joi from "joi";
import { TrainingType } from "../../model/TrainingType";

class UpdateTrainingPlanModal extends Form {

  state = {
    data: {
      _name: this.props.trainingPlan._name,
      trainingType: this.props.trainingPlan.trainingType,
      personalTrainingsNumber: this.props.trainingPlan.personalTrainingsNumber,
      price: this.props.trainingPlan.price
    },
    trainingTypes: [],
    modalId: 'updateTrainingPlanModal',
    name: 'updateTrainingPlan',
    errors: []
  }

  fieldRestrictions = {
    _name: Joi.string().required(),
    trainingType: Joi.any().required(),
    personalTrainingsNumber: Joi.number().min(0).required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    _name: this.fieldRestrictions._name,
    trainingType: this.fieldRestrictions.trainingType,
    personalTrainingsNumber: this.fieldRestrictions.personalTrainingsNumber,
    price: this.fieldRestrictions.price
  })

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('hidden.bs.modal', () => {
      let currentData = { ...this.state.data };
      currentData._name = this.props.trainingPlan._name;
      currentData.personalTrainingsNumber = this.props.trainingPlan.personalTrainingsNumber;
      currentData.price = this.props.trainingPlan.price;
      this.setState({ data: currentData });
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

  continueSubmitting = (e) => {
    const {
      _name,
      personalTrainingsNumber,
      price,
      trainingType
    } = this.props.trainingPlan;
    let updatedData = [];
    if (_name !== this.state.data._name) {
      updatedData.push(this.state.data._name)
    }
    if (trainingType !== this.state.data.trainingType) {
      updatedData.push(this.state.data.trainingType)
    }
    if (personalTrainingsNumber !== this.state.data.personalTrainingsNumber) {
      updatedData.push(this.state.data.personalTrainingsNumber)
    }
    if (price !== this.state.data.price) {
      updatedData.push(this.state.data.price)
    }
    console.log('Changed fields: ' + updatedData)
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
                {this.renderInput('_name', t('name'), 'text',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data._name)}
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