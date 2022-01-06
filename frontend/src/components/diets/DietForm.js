import Form from "../common/Form";
import Joi from "joi";
import 'bootstrap/dist/js/bootstrap';
import Dropdown from "../common/Dropdown";
import { DietType } from "../../model/DietType";
import { withTranslation } from "react-i18next";
import { createDiet, getDietTypes } from "../../services/DietService";

class DietForm extends Form {

  state = {
    data: {
      dietNumber: '',
      title: '',
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
    title: Joi.string().required(),
    dietType: Joi.any().required(),
    calories: Joi.number().min(100).required(),
    mealsNumber: Joi.number().min(1).required(),
    price: Joi.number().min(1).required()
  }

  schema = Joi.object({
    dietNumber: this.fieldRestrictions.dietNumber,
    title: this.fieldRestrictions.title,
    dietType: this.fieldRestrictions.dietType,
    calories: this.fieldRestrictions.calories,
    mealsNumber: this.fieldRestrictions.mealsNumber,
    price: this.fieldRestrictions.price
  })

  async componentDidMount() {
    let dietTypes = await getDietTypes();
    dietTypes = dietTypes.map(type => new DietType(type.title));
    let data = this.state.data;
    data.dietType = dietTypes[0].name;
    this.setState({
      data: data,
      dietTypes: dietTypes
    });
  }


  continueSubmitting = async () => {
    const { t } = this.props;
    const {
      dietNumber,
      title,
      dietType,
      calories,
      mealsNumber,
      price
    } = this.state.data;

    await createDiet({
      dietNumber: dietNumber,
      title: title,
      dietType: dietType.toUpperCase(),
      calories: parseInt(calories),
      mealsNumber: parseInt(mealsNumber),
      price: parseFloat(price)
    }, t).then(resp => {
      if (resp && resp.status === 201) {
        this.props.history.push('/diets');
      }
   });
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
            {this.renderInput("title", t("name"))}
            <Dropdown items={this.state.dietTypes}
                      itemName={t('dietType')}
                      propertyName='name'
                      buttonLabel={this.state.data.dietType}
                      onChangeBtn={this.handleDietTypeChange}/>
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