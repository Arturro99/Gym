import { withTranslation } from "react-i18next";
import '../../locales/i18n';
import { Diet } from "../../model/Diet";
import { Component } from "react";
import DietsTable from "./DietsTable";

class MyDietsComponent extends Component {

  state = {
    diets: [],
    pageSize: 8,
    currentPage: 1,
    sortColumn: { path: 'number', order: 'asc' }
  }

  paginatedDiets = {};

  componentDidMount() {
    const pathParam = this.props.match.params.login;
    this.setState({
      diets: [new Diet('DIE007', 'Nowa', 'Vege', 1200,
          2, 1200)]
    });
    //TODO fetch diets
  }

  handleDelete = diet => {
    const originalDiets = this.state.diets;
    const currentDiets = originalDiets.filter(
        die => die.number !== diet.number);
    this.setState({ diets: currentDiets });

    //TODO implement deletion
  }

  handleSort = sortColumn => {
    this.setState({ sortColumn });
  }

  render() {
    const { length: count } = this.state.diets;
    const {
      pageSize,
      currentPage,
      sortColumn,
      diets
    } = this.state;
    const { t } = this.props;

    if (count === 0) {
      return <h1>{t('noIndividualDiets')}</h1>;
    }

    return (
        <div className="row mt-5">
          <div className="col mt-5">
            <DietsTable diets={diets}
                        myTable={true}
                        sortColumn={sortColumn}
                        onDelete={this.handleDelete}
                        onUpdate={this.handleUpdate}
                        onApply={this.handleApply}
                        onSort={this.handleSort}/>
          </div>
        </div>
    );
  }
}

export default withTranslation()(MyDietsComponent);