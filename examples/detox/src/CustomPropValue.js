import React, { useState } from 'react'
import { Button, TextInput, Text } from 'react-native'

export default function CustomPropValue(props) {
    const [propName, setPropName] = useState("0")
    const [propValue, setPropValue] = useState("1")

    return (
        <>
            <Text>Prop value</Text>
            <TextInput
                testID="wheelIndex"
                style={style}
                onChangeText={setPropName}
                value={propName}
            />
            <Text>Scroll times</Text>
            <TextInput
                testID="scrollTimes"
                style={style}
                onChangeText={setPropValue}
                value={propValue}
            />
            <Button
                testID={"doScroll"}
                title={`Scroll`}
                onPress={() => props.scroll({ wheelIndex: Number.parseInt(propName), scrollTimes: Number.parseInt(propValue) })}
            />
            <Button
                testID={"reset"}
                title={`Reset`}
                onPress={props.reset}
            />
        </>
    )
}

const style = { height: 40, borderColor: 'gray', borderWidth: 1 }