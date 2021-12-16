import Details from "../common/Details";
import { withTranslation } from "react-i18next";
import { Activity } from "../../model/Activity";

class ActivityDetails extends Details {

  state = {
    data: {
      activity: Activity
    }
  }

  componentDidMount() {
    const pathParam = this.props.match.params.number;
    let currentState = { ...this.state };
    currentState.data.activity.number = pathParam;
    currentState.data.activity._name = 'Pobiegamy, poskaczemy';
    currentState.data.activity.trainer = 'trener';
    this.setState({ currentState });

    //TODO Populate details
  }

  render() {
    const { t } = this.props;
    return (
        <div className="card text-center shadow-lg mt-3 w-75 mx-auto">
          <div className="card-header">
            <h1>{t('activityDetails')}</h1>
          </div>
          {this.renderField('number', t('number'),
              this.state.data.activity)}
          {this.renderField('_name', t('name'), this.state.data.activity)}
          {this.renderField('startDate', t('startDate'),
              this.state.data.activity)}
          {this.renderField('duration',
              t('duration'), this.state.data.activity)}
          {this.renderField('trainer', t('trainer'),
              this.state.data.activity)}
          {this.renderField('active', t('active'), this.state.data.activity)}
          {this.renderField('capacity', t('capacity'), this.state.data.activity)}
          {this.renderField('createdBy', t('createdBy'),
              this.state.data.activity)}
          {this.renderField('modifiedBy', t('modifiedBy'),
              this.state.data.activity)}
          {this.renderField('creationDate', t('creationDate'),
              this.state.data.activity)}
          {this.renderField('modificationDate', t('modificationDate'),
              this.state.data.activity)}
        </div>
    );
  }
}

export default withTranslation()(ActivityDetails);