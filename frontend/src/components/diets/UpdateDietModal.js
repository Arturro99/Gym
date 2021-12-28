import Form from "../common/Form";
import { withTranslation } from "react-i18next";
import { DietType } from "../../model/DietType";
import Dropdown from "../common/Dropdown";
import Joi from "joi";
import { getDiet, updateDiet } from "../../services/DietService";

class UpdateDietModal extends Form {

  state = {
    data: {
      title: '',
      dietType: '',
      calories: '',
      mealsNumber: '',
      price: ''
    },
    dietTypes: [],
    modalId: 'updateDietModal',
    name: 'updateDiet',
    errors: []
  }

  fieldRestrictions = {
    title: Joi.string().required(),
    dietType: Joi.any().required(),
    calories: Joi.number().min(100).required(),
    mealsNumber: Joi.number().min(1).required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    title: this.fieldRestrictions.title,
    dietType: this.fieldRestrictions.dietType,
    calories: this.fieldRestrictions.calories,
    mealsNumber: this.fieldRestrictions.mealsNumber,
    price: this.fieldRestrictions.price
  })

  componentDidMount() {
    const myModalEl = document.getElementById(this.state.modalId)
    myModalEl.addEventListener('show.bs.modal', () => {
      this.resetData();
    })

    const type1 = new DietType('Low-Calorie');
    const type2 = new DietType('High-protein');
    const type3 = new DietType('Vege');
    this.setState({
      dietTypes: [type1, type2, type3]
    });
  }

  handleDietTypeChange = (type) => {
    let state = { ...this.state };
    state.data.dietType = type;
    this.setState({ state });
  }

  continueSubmitting = async () => {
    const {
      title,
      calories,
      mealsNumber,
      price,
      dietType,
      number
    } = this.props.diet;
    const { t } = this.props;
    let newTitle, newCalories, newMealsNumber, newPrice, newDietType = null;
    if (title !== this.state.data.title) {
      newTitle = this.state.data.title;
    }
    if (dietType !== this.state.data.dietType) {
      newDietType = this.state.data.dietType.toUpperCase();
    }
    if (calories !== this.state.data.calories) {
      newCalories = this.state.data.calories;
    }
    if (mealsNumber !== this.state.data.mealsNumber) {
      newMealsNumber = this.state.data.mealsNumber;
    }
    if (price !== this.state.data.price) {
      newPrice = this.state.data.price;
    }

    await updateDiet({
      number: number,
      title: newTitle,
      dietType: newDietType,
      calories: newCalories,
      mealsNumber: newMealsNumber,
      price: newPrice
    }, t)
  }

  async resetData() {
    let currentData = { ...this.state.data };
    const diet = await getDiet(this.props.diet.number);
    currentData.title = diet.title;
    currentData.dietType = diet.dietType;
    currentData.calories = diet.calories;
    currentData.mealsNumber = diet.mealsNumber;
    currentData.price = diet.price;
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