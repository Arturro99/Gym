import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { TrainingPlan } from "../../model/TrainingPlan";
import UpdateTrainingPlanModal from "./UpdateTrainingPlanModal";
import { parseFromOffsetDateTimeToLegibleFormat } from "../../services/DateParser";
import { getTrainingPlan } from "../../services/TrainingPlanService";
import config from '../../config.json'
import { getCurrentRole } from "../../services/AuthenticationService";

class TrainingPlanDetails extends Details {

  state = {
    data: {
      trainingPlan: TrainingPlan
    }
  }

  async componentDidMount() {
    await this.updateDetails();

    const myModalEl = document.getElementById('updateTrainingPlanModal')
    myModalEl.addEventListener('hidden.bs.modal', () => {
      this.updateDetails();
    })
  }

  async updateDetails() {
    const pathParam = this.props.match.params.number;
    let currentState = { ...this.state };
    const fetched = await getTrainingPlan(pathParam);
    currentState.data.trainingPlan = new TrainingPlan(
        fetched.number,
        fetched.title,
        fetched.trainingType,
        fetched.personalTrainingsNumber,
        fetched.trainer,
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
          <UpdateTrainingPlanModal trainingPlan={this.state.data.trainingPlan}/>
          <div className="card-header">
            <h1>{t('trainingPlanDetails')}</h1>
            {getCurrentRole() === config.TRAINER ?
                this.renderUpdateButton('updateTrainingPlanModal', t('update'))
                : ''}
          </div>
          {this.renderField('number', t('number'),
              this.state.data.trainingPlan)}
          {this.renderField('title', t('name'), this.state.data.trainingPlan)}
          {this.renderField('trainingType', t('trainingType'),
              this.state.data.trainingPlan)}
          {this.renderField('personalTrainingsNumber',
              t('personalTrainingsNumber'), this.state.data.trainingPlan)}
          {this.renderField('trainer', t('trainer'),
              this.state.data.trainingPlan)}
          {this.renderField('price', t('price'), this.state.data.trainingPlan)}
          {this.renderField('createdBy', t('createdBy'),
              this.state.data.trainingPlan, true)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.trainingPlan, true)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.trainingPlan)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.trainingPlan)}
        </div>
    );
  }
}

export default withTranslation()(TrainingPlanDetails);