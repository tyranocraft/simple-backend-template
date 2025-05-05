import styles from './SampleButton.module.scss'

export interface SampleButtonProps {
    label: string
    onClick?: () => void
}

export default function SampleButton({ label, onClick }: SampleButtonProps) {
    return (
        <button className={styles.sampleButton} onClick={onClick}>
            {label}
        </button>
    )
}