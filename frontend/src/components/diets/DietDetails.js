import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { Diet } from "../../model/Diet";

class DietDetails extends Details {

  state = {
    data: {
      diet: Diet
    }
  }

  componentDidMount() {
    const pathParam = this.props.match.params.number;
    let currentState = { ...this.state };
    currentState.data.diet.number = pathParam;
    currentState.data.diet._name = 'Dieta świetna';
    currentState.data.diet.calories = '1500';
    this.setState({ currentState });

    //TODO Populate details
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <div className="card-header">
            <h1>{t('dietDetails')}</h1>
          </div>
          {this.renderField('number', t('number'),
              this.state.data.diet)}
          {this.renderField('_name', t('name'), this.state.data.diet)}
          {this.renderField('dietType', t('dietType'),
              this.state.data.diet)}
          {this.renderField('calories',
              t('calories'), this.state.data.diet)}
          {this.renderField('mealsNumber', t('mealsNumber'),
              this.state.data.diet)}
          {this.renderField('price', t('price'), this.state.data.diet)}
        </div>
    );
  }
}

export default withTranslation()(DietDetails);