import React, { useState } from 'react'
import { Button, TextInput, Text, View, TouchableOpacity } from 'react-native'

export default function CustomPropValue(props) {
    const [propName, setPropName] = useState("")
    const [propValue, setPropValue] = useState("")

    const getPropValue = () => {
        if (propValue === "undefined") return undefined
        if (propName === "minuteInterval") return parseInt(propValue)
        return propValue
    }

    return (
        <View style={{
            flexDirection: 'row',
            marginBottom: 15,
        }}>
            <Text>Prop name</Text>
            <TextInput
                testID="propName"
                style={style}
                onChangeText={setPropName}
                value={propName}
            />
            <Text>Prop value</Text>
            <TextInput
                testID="propValue"
                style={style}
                onChangeText={setPropValue}
                value={propValue}
            />
            <TouchableOpacity
                testID={"changeProp"}
                onPress={() => props.changeProp({ propName, propValue: getPropValue() })}
            ><Text>Change</Text></TouchableOpacity>
        </View>
    )
}

const style = { height: 40, borderColor: 'gray', borderWidth: 0.5, margin: 2 }