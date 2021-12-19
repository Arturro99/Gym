import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { TrainingPlan } from "../../model/TrainingPlan";
import UpdateTrainingPlanModal from "./UpdateTrainingPlanModal";

class TrainingPlanDetails extends Details {

  state = {
    data: {
      trainingPlan: TrainingPlan
    }
  }

  componentDidMount() {
    const pathParam = this.props.match.params.number;
    let currentState = { ...this.state };
    currentState.data.trainingPlan.number = pathParam;
    currentState.data.trainingPlan._name = 'Jakas nazwa';
    currentState.data.trainingPlan.trainer = 'trener';
    this.setState({ currentState });

    //TODO Populate details
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <UpdateTrainingPlanModal trainingPlan={this.state.data.trainingPlan}/>
          <div className="card-header">
            <h1>{t('trainingPlanDetails')}</h1>
            {this.renderUpdateButton('updateTrainingPlanModal', t('update'))}
          </div>
          {this.renderField('number', t('number'),
              this.state.data.trainingPlan)}
          {this.renderField('_name', t('name'), this.state.data.trainingPlan)}
          {this.renderField('trainingType', t('trainingType'),
              this.state.data.trainingPlan)}
          {this.renderField('personalTrainingsNumber',
              t('personalTrainingsNumber'), this.state.data.trainingPlan)}
          {this.renderField('trainer', t('trainer'),
              this.state.data.trainingPlan)}
          {this.renderField('price', t('price'), this.state.data.trainingPlan)}
          {this.renderField('createdBy', t('createdBy'),
              this.state.data.trainingPlan)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.trainingPlan)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.trainingPlan)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.trainingPlan)}
        </div>
    );
  }
}

export default withTranslation()(TrainingPlanDetails);