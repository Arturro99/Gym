import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { Diet } from "../../model/Diet";
import UpdateDietModal from "./UpdateDietModal";
import { getDiet } from "../../services/DietService";
import { parseFromOffsetDateTimeToLegibleFormat } from "../../services/DateParser";
import { getCurrentRole } from "../../services/AuthenticationService";
import config from "../../config.json";

class DietDetails extends Details {

  state = {
    data: {
      diet: Diet
    }
  }

  async componentDidMount() {
    await this.updateDetails();

    const myModalEl = document.getElementById('updateDietModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      this.updateDetails();
    })
  }

  updateDetails = async () => {
    const pathParam = this.props.match.params.number;
    let currentState = { ...this.state };
    const fetched = await getDiet(pathParam);
    currentState.data.diet = new Diet(
        fetched.number,
        fetched.title,
        fetched.dietType,
        fetched.calories,
        fetched.mealsNumber,
        fetched.price,
        fetched.modifiedBy,
        fetched.createdBy,
        parseFromOffsetDateTimeToLegibleFormat(fetched.creationDate),
        parseFromOffsetDateTimeToLegibleFormat(fetched.modificationDate)
    );
    this.setState({ currentState });
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <UpdateDietModal diet={this.state.data.diet}
                           updateDetails={this.updateDetails}/>
          <div className="card-header">
            <h1>{t('dietDetails')}</h1>
            {this.renderRefreshButton(this.updateDetails, t)}
            {getCurrentRole() === config.TRAINER ?
                this.renderUpdateButton('updateDietModal', t('update')) : ''}
          </div>
          {this.renderField('number', t('number'),
              this.state.data.diet)}
          {this.renderField('title', t('name'), this.state.data.diet)}
          {this.renderField('dietType', t('dietType'),
              this.state.data.diet)}
          {this.renderField('calories',
              t('calories'), this.state.data.diet)}
          {this.renderField('mealsNumber', t('mealsNumber'),
              this.state.data.diet)}
          {this.renderField('price', t('price'), this.state.data.diet)}
          {this.renderField('createdBy', t('createdBy'),
              this.state.data.diet, true)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.diet, true)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.diet)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.diet)}
        </div>
    );
  }
}

export default withTranslation()(DietDetails);