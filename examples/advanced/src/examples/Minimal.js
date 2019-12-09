import React, { Component } from "react";
import { Text } from "react-native";
import DatePicker from "react-native-date-picker";

export const readableDate = date =>
  date
    ? date
        .toISOString()
        .substr(0, 19)
        .replace("T", " ")
    : "undefined";

export default class MinimalExample extends Component {
  state = { date: new Date() };

  render = () => (
    <>
      <DatePicker
        date={this.state.date}
        // mode={"time"}
        onDateChange={this.handleDatePicked}
        locale={"en_GB"}
      />
      <Text>Picker date: {readableDate(this.state.date)}</Text>
    </>
  );

  handleDatePicked = date => {
    console.log("hej");

    this.setState({ date });
  };
}
