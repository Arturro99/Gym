import TableHeader from "./TableHeader";
import TableBody from "./TableBody";
import React from "react";

const Table = (props) => {

  const { columns, sortColumn, data, onSort, onRefresh, t } = props;

  return (
      <div>
        <button className="btn btn-primary float-end mb-2"
                onClick={onRefresh}>{t('refresh')}</button>
        <table
            className="table align-baseline table-striped table-bordered text-center mt-5">
          <TableHeader columns={columns}
                       sortColumn={sortColumn}
                       onSort={onSort}/>
          <TableBody data={data}
                     columns={columns}/>
        </table>
      </div>
  )
}

export default Table;