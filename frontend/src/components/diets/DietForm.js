import Form from "../common/Form";
import Joi from "joi";
import 'bootstrap/dist/js/bootstrap';
import Dropdown from "../common/Dropdown";
import { DietType } from "../../model/DietType";
import { withTranslation } from "react-i18next";

class DietForm extends Form {

  state = {
    data: {
      dietNumber: '',
      name: '',
      dietType: '',
      calories: '',
      mealsNumber: '',
      price: ''
    },
    errors: {},
    dietTypes: []
  };

  fieldRestrictions = {
    dietNumber: Joi.string().pattern(/^DIE[0-9]{3}$/).required(),
    name: Joi.string().required(),
    dietType: Joi.any().required(),
    calories: Joi.number().min(100).required(),
    mealsNumber: Joi.number().min(1).required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    dietNumber: this.fieldRestrictions.dietNumber,
    name: this.fieldRestrictions.name,
    dietType: this.fieldRestrictions.dietType,
    calories: this.fieldRestrictions.calories,
    mealsNumber: this.fieldRestrictions.mealsNumber,
    price: this.fieldRestrictions.price
  })

  componentDidMount() {
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

  continueSubmitting = () => {
    console.log(
        `Diet: ${this.state.data.dietNumber} created ${this.state.data.dietType}`)
  }

  handleDietTypeChange = (type) => {
    let state = { ...this.state };
    state.data.dietType = type;
    this.setState({ state });
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card-header mt-5 w-50 mx-auto">
          <h1 className="text-center">{t('newDiet')}</h1>
          <form onSubmit={this.handleSubmit}>
            {this.renderInput("dietNumber", t("number"))}
            {this.renderInput("name", t("name"))}
            <Dropdown items={this.state.dietTypes}
                      itemName={t('dietType')}
                      propertyName='name'
                      buttonLabel={this.state.data.dietType}
                      onChangeBtn={this.handleDietTypeChange}
                      style={{right: 0}}/>
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