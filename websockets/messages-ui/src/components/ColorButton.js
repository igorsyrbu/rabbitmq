import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles({
    root: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        borderRadius: 5,
        border: 0,
        color: 'white',
        height: 48,
        padding: '0 20px',
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    }
});

export default function ColorButton(props) {
    const classes = useStyles();

    return (
        <Button
            style={{margin: '1%'}}
            classes={{root: classes.root}}
            onClick={props.onClick}
        >
            {props.title}
        </Button>
    );
}