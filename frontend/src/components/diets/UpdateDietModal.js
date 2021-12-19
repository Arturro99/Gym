import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import { DietType } from "../../model/DietType";
import Dropdown from "../common/Dropdown";
import Joi from "joi";

class UpdateDietModal extends Form {

  state = {
    data: {
      _name: this.props.diet._name,
      dietType: this.props.diet.dietType,
      calories: this.props.diet.calories,
      mealsNumber: this.props.diet.mealsNumber,
      price: this.props.diet.price
    },
    dietTypes: [],
    modalId: 'updateDietModal',
    name: 'updateDiet',
    errors: []
  }

  fieldRestrictions = {
    _name: Joi.string().required(),
    dietType: Joi.any().required(),
    calories: Joi.number().min(100).required(),
    mealsNumber: Joi.number().min(1).required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    _name: this.fieldRestrictions._name,
    dietType: this.fieldRestrictions.dietType,
    calories: this.fieldRestrictions.calories,
    mealsNumber: this.fieldRestrictions.mealsNumber,
    price: this.fieldRestrictions.price
  })

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('hidden.bs.modal', () => {
      let currentData = { ...this.state.data };
      currentData._name = this.props.diet._name;
      currentData.calories = this.props.diet.calories;
      currentData.mealsNumber = this.props.diet.mealsNumber;
      currentData.price = this.props.diet.price;
      this.setState({ data: currentData });
    })

    const type1 = new DietType('Low-Calorie');
    const type2 = new DietType('High-protein');
    const type3 = new DietType('Vege');

    let mockedData = this.state.data;
    mockedData.dietType = 'High-protein';
    this.setState({
      data: mockedData,
      dietTypes: [type1, type2, type3]
    });
  }

  handleDietTypeChange = (type) => {
    let state = { ...this.state };
    state.data.dietType = type;
    this.setState({ state });
  }

  continueSubmitting = (e) => {
    const { _name, calories, mealsNumber, price, dietType } = this.props.diet;
    let updatedData = [];
    if (_name !== this.state.data._name) {
      updatedData.push(this.state.data._name)
    }
    if (dietType !== this.state.data.dietType) {
      updatedData.push(this.state.data.dietType)
    }
    if (calories !== this.state.data.calories) {
      updatedData.push(this.state.data.calories)
    }
    if (mealsNumber !== this.state.data.mealsNumber) {
      updatedData.push(this.state.data.mealsNumber)
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
              <Dropdown items={this.state.dietTypes}
                        itemName={t('dietType')}
                        propertyName='name'
                        buttonLabel={this.state.data.dietType}
                        onChangeBtn={this.handleDietTypeChange}
                        labelClassName={"text-light"}
                        style={{ left: 0 }}/>
              <div className="modal-body ms-4">
                {this.renderInput('calories', t('calories'), 'number',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.calories)}
              </div>
              <div className="modal-body ms-4">
                {this.renderInput('mealsNumber', t('mealsNumber'), 'number',
                    'login-label',
                    'login-box position-relative start-50 translate-middle-x text-light',
                    data.mealsNumber)}
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

export default withTranslation()(UpdateDietModal);