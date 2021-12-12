import { withTranslation } from "react-i18next";
import Form from "../common/Form";
import Joi from "joi";

class DietForm extends Form {

  state = {
    data: {
      dietNumber: '',
      name: '',
      calories: '',
      mealsNumber: '',
      price: ''
    },
    errors: {}
  };

  fieldRestrictions = {
    dietNumber: Joi.string().pattern(/^DIE[0-9]{3}$/).required(),
    name: Joi.string().required(),
    calories: Joi.number().min(100).required(),
    mealsNumber: Joi.number().min(1).required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    dietNumber: this.fieldRestrictions.dietNumber,
    name: this.fieldRestrictions.name,
    calories: this.fieldRestrictions.calories,
    mealsNumber: this.fieldRestrictions.mealsNumber,
    price: this.fieldRestrictions.price
  })

  continueSubmitting = () => {
    console.log(`Diet: ${this.state.data.dietNumber} created`)
  }

  render() {
    const { t } = this.props;
    return (
        <div>
          <h1 className="modal-header">{t('newDiet')}</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("dietNumber", t("number"))}
            {this.renderInput("name", t("name"))}
            {this.renderInput("calories", t("calories"), 'number')}
            {this.renderInput("mealsNumber", t("mealsNumber"), 'number')}
            {this.renderInput("price", t("price"), 'number')}
            {this.renderButton(t("create"))}
          </form>
        </div>
    );
  }
}

export default withTranslation()(DietForm);